package com.lavalliere.daniel.spring.kafkaorderdispatch.service;

import com.lavalliere.daniel.spring.kafkaorderdispatch.handler.OrderCreatedHandler;
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

    @Value("${dispatch.producer.topic:#{null}}")
    private String topic = null;

    private OrderCreated payload;
    private OrderDispatched expectedDispatched;

    @Mock
    private KafkaTemplate<String, Object> kafkaProducerMock;

    private DispatchService dispatcherService;

    @BeforeEach
    void setup() {
        dispatcherService = new DispatchService(kafkaProducerMock);
        UUID orderId = UUID.randomUUID();
        payload = OrderCreated.builder()
            .orderId(orderId)
            .item(orderId.toString())
            .build();
        expectedDispatched = OrderDispatched.builder()
            .orderId(orderId)
            .build();
    }

    @Test
    void test_process() throws ExecutionException, InterruptedException {
        when(kafkaProducerMock.send(eq(topic), eq(expectedDispatched))).thenReturn(mock(CompletableFuture.class)); // Mock the send method to return null or a Future
        dispatcherService.process(payload);
        verify(kafkaProducerMock, times(1)).send(eq(topic), eq(expectedDispatched));
    }

    @Test
    void test_process_producer_throws_exception() {
        doThrow(new RuntimeException("Producer failure")).when(kafkaProducerMock).send(eq(topic), eq(expectedDispatched));
        Exception exception = assertThrows(RuntimeException.class, () -> dispatcherService.process(payload));
        verify(kafkaProducerMock, times(1)).send(eq(topic), eq(expectedDispatched));
        assertThat(exception.getMessage(), equalTo("Producer failure"));
    }
}