package com.lavalliere.daniel.spring.kafkaorderdispatch.handler;

import com.lavalliere.daniel.spring.kafkaorderdispatch.message.OrderCreated;
import com.lavalliere.daniel.spring.kafkaorderdispatch.service.DispatchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// @ExtendWith(MockitoExtension.class)   // Alternate using @Mock instead of @MockitoBean
@ExtendWith(SpringExtension.class)       // Or @SpringBootTest
class OrderCreatedHandlerTest {

    private OrderCreatedHandler handler;
    private OrderCreated payload;
    private final String key = UUID.randomUUID().toString();
    private int partitionId = 0;

    @MockitoBean
    private DispatchService dispatcherServiceMockBean;

    @BeforeEach
    void setup() {
        handler = new OrderCreatedHandler(dispatcherServiceMockBean);
        UUID orderId = UUID.randomUUID();
        payload = OrderCreated.builder()
                .orderId(orderId)
                .item(orderId.toString())
                .build();
    }

    @Test
    void test_listen_success() throws ExecutionException, InterruptedException {
        handler.listen(partitionId, key, payload);
        verify(dispatcherServiceMockBean, times(1)).process(key, payload);
    }

    @Test
    void test_listen_service_throws_exception() throws ExecutionException, InterruptedException {
        doThrow(new RuntimeException("Service failure")).when(dispatcherServiceMockBean).process(key, payload);
        Exception exception = assertThrows(RuntimeException.class, () -> handler.listen(partitionId, key, payload));
        verify(dispatcherServiceMockBean, times(1)).process(key, payload);
    }
}