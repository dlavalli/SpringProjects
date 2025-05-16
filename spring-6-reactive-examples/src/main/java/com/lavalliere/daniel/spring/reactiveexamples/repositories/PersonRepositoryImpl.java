package com.lavalliere.daniel.spring.reactiveexamples.repositories;

import com.lavalliere.daniel.spring.reactiveexamples.domain.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;



public class PersonRepositoryImpl implements PersonRepository {
    Person michael = Person.builder().id(1).firstName("Michael").lastName("Weston").build();
    Person fiona = Person.builder().id(2).firstName("Fiona").lastName("Glenanne").build();
    Person sam = Person.builder().id(3).firstName("Sam").lastName("Axe").build();
    Person jesse = Person.builder().id(4).firstName("Jesse").lastName("Porter").build();

    @Override
    public Mono<Person> getById(final Integer id) {
        // Do not actually perform a call, just return the specific value or empty
        // return (persons.get(id) == null ?  Mono.empty() : Mono.just(persons.get(id)));
        return findAll().filter(person -> person.getId().equals(id)).next();
    }

    @Override
    public Flux<Person> findAll() {
        // Both are possible
        // return Flux.fromIterable(List.of(michael, fiona, sam, jesse));
        return Flux.just(michael, fiona, sam, jesse);
    }
}
