package com.lavalliere.daniel.spring.kafkaorderdispatch.service;

import com.lavalliere.daniel.spring.kafkaorderdispatch.handler.OrderCreatedHandler;
import com.lavalliere.daniel.spring.kafkaorderdispatch.message.DispatchPreparing;
import com.lavalliere.daniel.spring.kafkaorderdispatch.message.OrderCreated;
import com.lavalliere.daniel.spring.kafkaorderdispatch.message.OrderDispatched;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)   // Alternate using @Mock instead of @MockitoBean
// @ExtendWith(SpringExtension.class) // Or @SpringBootTest
class DispatchServiceTest {
    private final String applicationId = UUID.randomUUID().toString();
    private OrderCreated payload;

    @Mock
    private KafkaTemplate<String, Object> kafkaProducerMock;

    private DispatchService dispatcherService;

    @BeforeEach
    void setup() {
        dispatcherService = new DispatchService(kafkaProducerMock,applicationId);
        UUID orderId = UUID.randomUUID();
        payload = OrderCreated.builder()
            .orderId(orderId)
            .item(orderId.toString())
            .build();
    }

    @Test
    void test_process() throws ExecutionException, InterruptedException {
        when(kafkaProducerMock.send(eq(DispatchService.ORDER_DISPATCHED_TOPIC), any(OrderDispatched.class))).thenReturn(mock(CompletableFuture.class)); // Mock the send method to return null or a Future
        when(kafkaProducerMock.send(eq(DispatchService.DISPATCH_TRACKING_TOPIC), any(DispatchPreparing.class))).thenReturn(mock(CompletableFuture.class)); // Mock the send method to return null or a Future
        dispatcherService.process(payload);
        verify(kafkaProducerMock, times(1)).send(eq(DispatchService.ORDER_DISPATCHED_TOPIC), any(OrderDispatched.class));
        verify(kafkaProducerMock, times(1)).send(eq(DispatchService.DISPATCH_TRACKING_TOPIC), any(DispatchPreparing.class));
    }

    @Test
    void test_process_producer_throws_exception() {
        doThrow(new RuntimeException("Dispatch tracking producer failure")).when(kafkaProducerMock).send(eq(DispatchService.DISPATCH_TRACKING_TOPIC), any(DispatchPreparing.class));
        Exception exception = assertThrows(RuntimeException.class, () -> dispatcherService.process(payload));
        verify(kafkaProducerMock, times(1)).send(eq(DispatchService.DISPATCH_TRACKING_TOPIC), any(DispatchPreparing.class));
        verifyNoMoreInteractions(kafkaProducerMock);
        assertThat(exception.getMessage(), equalTo("Dispatch tracking producer failure"));
    }

    @Test
    void test_process_dispatched_throws_exception() {
        when(kafkaProducerMock.send(anyString(), any(DispatchPreparing.class))).thenReturn(mock(CompletableFuture.class)); // Mock the send method to return null or a Future for the first topic
        doThrow(new RuntimeException("Order dispatched producer failure")).when(kafkaProducerMock).send(eq(DispatchService.ORDER_DISPATCHED_TOPIC), any());
        Exception exception = assertThrows(RuntimeException.class, () -> dispatcherService.process(payload));
        verify(kafkaProducerMock, times(1)).send(eq(DispatchService.DISPATCH_TRACKING_TOPIC), any(DispatchPreparing.class));
        verify(kafkaProducerMock, times(1)).send(eq(DispatchService.ORDER_DISPATCHED_TOPIC), any(OrderDispatched.class));
        assertThat(exception.getMessage(), equalTo("Order dispatched producer failure"));
    }
}