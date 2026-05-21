package com.lavalliere.daniel.spring.kafkaorderdispatch.service;

import com.lavalliere.daniel.spring.kafkaorderdispatch.message.DispatchPreparing;
import com.lavalliere.daniel.spring.kafkaorderdispatch.message.OrderCreated;
import com.lavalliere.daniel.spring.kafkaorderdispatch.message.OrderDispatched;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Slf4j
@RequiredArgsConstructor
@Service
public class DispatchService {

    public static final String DISPATCH_TRACKING_TOPIC = "dispatch.tracking";
    public static final String ORDER_DISPATCHED_TOPIC = "order.dispatched";

    private final KafkaTemplate<String, Object> kafkaProducer;

    public void process(OrderCreated orderCreated) throws ExecutionException, InterruptedException {
        DispatchPreparing dispatchPreparing  = DispatchPreparing.builder()
            .orderId(orderCreated.orderId())
            .build();
        // Async by default. Will fire and forget and to catch error would have top register a listener or use Synchronous version and manage exceptions
        // Calling .get() makes the call sync
        kafkaProducer.send(DISPATCH_TRACKING_TOPIC, dispatchPreparing).get();

        OrderDispatched orderDispatched = OrderDispatched.builder()
                .orderId(orderCreated.orderId())
                .build();
        // Async by default. Will fire and forget and to catch error would have top register a listener or use Synchronous version and manage exceptions
        // Calling .get() makes the call sync
        kafkaProducer.send(ORDER_DISPATCHED_TOPIC, orderDispatched).get();
    }
}
