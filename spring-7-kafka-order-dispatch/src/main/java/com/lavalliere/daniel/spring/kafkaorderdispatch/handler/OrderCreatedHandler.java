package com.lavalliere.daniel.spring.kafkaorderdispatch.handler;

import com.lavalliere.daniel.spring.kafkaorderdispatch.message.OrderCreated;
import com.lavalliere.daniel.spring.kafkaorderdispatch.service.DispatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Slf4j
@RequiredArgsConstructor  // All fields uninitialized and private final or marked as @NotNull
@Component
@KafkaListener(
    id = "orderConsumerClient",
    topics = "order.created",
    groupId = "dispatch.order.created.consumer",
    containerFactory = "kafkaListenerContainerFactory"
)
public class OrderCreatedHandler {

    private final DispatchService dispatcher;

    // Can define multiple handlers to handle multiple event types from the same topic
    @KafkaHandler
    public void listen(
        @Header(KafkaHeaders.RECEIVED_PARTITION)  int partition,
        @Header(KafkaHeaders.RECEIVED_KEY) String key,
        @Payload OrderCreated payload
    ) throws ExecutionException, InterruptedException {
        log.info("Received message: partition: {} - key: {} - payload: {}", partition, key, payload);
        dispatcher.process(key, payload);
    }
}
