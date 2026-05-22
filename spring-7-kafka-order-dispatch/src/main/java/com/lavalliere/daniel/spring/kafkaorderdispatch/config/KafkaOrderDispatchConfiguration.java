package com.lavalliere.daniel.spring.kafkaorderdispatch.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;
import com.lavalliere.daniel.spring.kafkaorderdispatch.message.OrderCreated;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@ComponentScan(basePackages = {"com.lavalliere.daniel.spring.kafkaorderdispatch"})
public class KafkaOrderDispatchConfiguration {

    // Using the header's event type, you need to specify the packages that are trusted to deserialize the events to.
    // This is a security measure to prevent deserialization of malicious classes that could be included in the header
    // This is a comma separated listed of packages and can include wildcards
    private final static String TRUSTED_PACKAGES = "com.lavalliere.daniel.spring.kafkaorderdispatch.message";

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(
        ConsumerFactory<String, Object> consumerFactory
    ) {
        log.info("Creating MY ConcurrentKafkaListenerContainerFactory bean instance");
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, Object>  consumerFactory(
        @Value("${kafka.bootstrap-servers}") String bootstrapServers
    ) {
        log.info("Creating MY ConsumerFactory");
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JacksonJsonDeserializer.class);  // None defined by default in kafka
        // config.put(JacksonJsonDeserializer.VALUE_DEFAULT_TYPE, OrderCreated.class);                  // Cannot rely on this when supporting multiple event types as the POJO will differ accordingly
                                                                                                        // instead, rely on the message header which includes the event type
                                                                                                        // Spring Kafka will use this to select the type to deserialize the event to
                                                                                                        // Spring Kafka automatically adds this header when it produces messages unless configured not to
        config.put(JacksonJsonDeserializer.TRUSTED_PACKAGES, TRUSTED_PACKAGES);                         // You also need to specify the allowed types to be used in association with the event type header

        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, Object>  kafkaTemplate(ProducerFactory<String, Object> producerFactory) {
        log.info("Creating MY KafkaTemplate");
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    ProducerFactory<String, Object> producerFactory(@Value("${kafka.bootstrap-servers}") String bootstrapServers) {
        log.info("Creating MY ProducerFactory");
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JacksonJsonSerializer.class);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }
}
