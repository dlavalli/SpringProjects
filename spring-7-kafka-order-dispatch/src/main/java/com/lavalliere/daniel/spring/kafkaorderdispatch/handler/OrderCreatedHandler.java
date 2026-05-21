package com.lavalliere.daniel.spring.kafkaorderdispatch.handler;

import com.lavalliere.daniel.spring.kafkaorderdispatch.message.OrderCreated;
import com.lavalliere.daniel.spring.kafkaorderdispatch.service.DispatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Slf4j
@RequiredArgsConstructor  // All fields uninitialized and private final or marked as @NotNull
@Component
public class OrderCreatedHandler {

    private final DispatchService dispatcher;

    // Register a consumer on kafka topics
    @KafkaListener(
        id = "orderConsumerClient",
        topics = "order.created",
        groupId = "dispatch.order.created.consumer",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void listen(OrderCreated payload) throws ExecutionException, InterruptedException {
        log.info("Received message: payload: {}", payload);
        dispatcher.process(payload);
    }
}
