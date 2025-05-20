package com.lavalliere.daniel.spring.reactive_mongo.repositories;

import com.lavalliere.daniel.spring.reactive_mongo.domain.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {
}
