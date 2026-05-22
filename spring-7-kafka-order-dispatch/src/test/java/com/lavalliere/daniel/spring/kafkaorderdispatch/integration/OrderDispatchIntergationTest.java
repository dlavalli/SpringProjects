package com.lavalliere.daniel.spring.kafkaorderdispatch.integration;

import com.lavalliere.daniel.spring.kafkaorderdispatch.config.KafkaOrderDispatchConfiguration;
import com.lavalliere.daniel.spring.kafkaorderdispatch.message.DispatchPreparing;
import com.lavalliere.daniel.spring.kafkaorderdispatch.message.OrderCreated;
import com.lavalliere.daniel.spring.kafkaorderdispatch.message.OrderDispatched;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@Slf4j
@SpringBootTest(classes = {KafkaOrderDispatchConfiguration.class })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS) // No context reload between tests
@ActiveProfiles("test")
@EmbeddedKafka(controlledShutdown = true)  // So the tests end cleanly
public class OrderDispatchIntergationTest {

    private void sendMessage(String topic, String key, Object payload) throws Exception {
        kafkaTemplate.send(MessageBuilder
            .withPayload(payload)
            .setHeader(KafkaHeaders.TOPIC, topic)
            .setHeader(KafkaHeaders.KEY, key)
            .build()
        ).get();
    }

    private final static String ORDER_CREATED_TOPIC = "order.created";
    private final static String ORDER_DISPATCHED_TOPIC = "order.dispatched";
    private final static String DISPATCH_TRACKING_TOPIC = "dispatch.tracking";
    public final static String key = UUID.randomUUID().toString();
    private int partitionId = 0;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private KafkaListenerEndpointRegistry registry;

    @Autowired
    private KafkaTestListener testListener;

    @Configuration
    static class TestConfig {
        @Bean
        public KafkaTestListener testListener() {
            return new KafkaTestListener();
        }
    }

    public static class KafkaTestListener {
        AtomicInteger dispatchPreparingCounter = new AtomicInteger(0);
        AtomicInteger orderDispatchCounter = new AtomicInteger(0);

        @KafkaListener(
            groupId="KafkaIntegrationTest",
            topics=DISPATCH_TRACKING_TOPIC
        )
        void receiveDispatchPreparing(@Payload DispatchPreparing payload) {
            log.info("Received DispatchPreparing: key: {} payload: {}", key, payload);
            assertThat(key, notNullValue());
            assertThat(payload, notNullValue());
            dispatchPreparingCounter.incrementAndGet();
        }

        @KafkaListener(
            groupId="KafkaIntegrationTest",
            topics=ORDER_DISPATCHED_TOPIC
        )
        void receiveOrderDispatch(
            @Header(KafkaHeaders.RECEIVED_KEY) String key,
            @Payload OrderDispatched payload
        ) {
            log.info("Received OrderDispatched: key: {} payload: {}", key, payload);
            assertThat(key, notNullValue());
            assertThat(payload, notNullValue());
            orderDispatchCounter.incrementAndGet();
        }
    }

    @BeforeEach
    public void setUp() {
        testListener.dispatchPreparingCounter.set(0);
        testListener.orderDispatchCounter.set(0);
        registry.getListenerContainers().forEach(container -> {
            ContainerTestUtils.waitForAssignment(container, embeddedKafkaBroker.getPartitionsPerTopic());
        });
    }

    @Test
    public void testOrderDispatchFlow() throws Exception {
        OrderCreated orderCreated = OrderCreated.builder().orderId(UUID.randomUUID()).item("my-item").build();
        sendMessage(ORDER_CREATED_TOPIC, key, orderCreated);
        await()
            .atMost(3, TimeUnit.SECONDS)
            .pollDelay(100, TimeUnit.MILLISECONDS)
            .until(testListener.dispatchPreparingCounter::get, equalTo(1));
        await()
            .atMost(1, TimeUnit.SECONDS)
            .pollDelay(100, TimeUnit.MILLISECONDS)
            .until(testListener.orderDispatchCounter::get, equalTo(1));
    }
}
