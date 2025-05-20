package com.lavalliere.daniel.spring.reactive_mongo;

import com.lavalliere.daniel.spring.reactive_mongo.mappers.BeerMapperImpl;
import com.lavalliere.daniel.spring.reactive_mongo.mappers.CustomerMapperImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({BeerMapperImpl.class, CustomerMapperImpl.class})
public class Spring6ReactiveMongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(Spring6ReactiveMongoApplication.class, args);
	}

}
