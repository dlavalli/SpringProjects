package com.lavalliere.daniel.spring.kafkaorderdispatch;

import com.lavalliere.daniel.spring.kafkaorderdispatch.config.KafkaOrderDispatchConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
public class KafkaOrderDispatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaOrderDispatchApplication.class, args);
	}

}
