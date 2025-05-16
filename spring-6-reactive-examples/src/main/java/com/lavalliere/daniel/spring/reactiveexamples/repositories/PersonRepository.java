package com.lavalliere.daniel.spring.reactiveexamples.repositories;

import com.lavalliere.daniel.spring.reactiveexamples.domain.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonRepository {
    Mono<Person> getById(Integer id);
    Flux<Person> findAll();
}
