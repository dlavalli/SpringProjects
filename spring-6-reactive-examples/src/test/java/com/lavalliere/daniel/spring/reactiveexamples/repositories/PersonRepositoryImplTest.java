package com.lavalliere.daniel.spring.reactiveexamples.repositories;

import com.lavalliere.daniel.spring.reactiveexamples.domain.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonRepositoryImplTest {

    @Autowired
    private PersonRepository personRepository = new PersonRepositoryImpl();

    @Test
    void getMonoByIdBADBlocking() {
        Mono<Person>  personMono = personRepository.getById(1);
        Person person = personMono.block();   // Block IS NOT a good way to test
                                              // but since value is hardcoded, no side effect in this test
        System.out.println(person.toString());
    }

    @Test
    void getMonoByIdWithSubscribe() {
        Mono<Person>  personMono = personRepository.getById(1);
        personMono.subscribe(System.out::println);
    }

    @Test
    void getByIdMapOperationWithSubscribe() {
        Mono<Person> personMono = personRepository.getById(1);
        personMono.map(person -> {
                return person.getFirstName();
            }
        ).subscribe(System.out::println);  // This is required to launch the operation (ie: make the async call)
    }

    @Test
    void testFluxBlockFirst() {
        Flux<Person> personFlux = personRepository.findAll();
        Person person = personFlux.blockFirst();
        System.out.println(person.toString());  // BOCK but only wait for 1st element. Do not worry about the rest
    }

    @Test
    void testFluxSubsriber() {
        Flux<Person> personFlux = personRepository.findAll();
        personFlux.subscribe(System.out::println);  // Executed for each element
    }

    @Test
    void testFluxMap() {
        Flux<Person> personFlux = personRepository.findAll();
        personFlux.map(person -> {
                return person.getFirstName();  // return required when using a block
            }
        ).subscribe(System.out::println);
    }

    @Test
    void testFluxToList() {
        Flux<Person> personFlux = personRepository.findAll();
        Mono<List<Person>> listMono = personFlux.collectList();  // Convert the flux to a single list mono
        listMono.subscribe(list -> {
            list.stream().map(Person::getFirstName).forEach(System.out::println);
        });
    }

    @Test
    void testFilterOnName() {
        personRepository.findAll().filter(person -> {
            return person.getFirstName().equalsIgnoreCase("Fiona");
        }).map(Person::getLastName)
          .subscribe(System.out::println);
    }

    @Test
    void testGetById() {
        personRepository.findAll().filter(person -> {
            return person.getFirstName().equalsIgnoreCase("Fiona");
        }).map(Person::getFirstName)
          .next()  // Returns the matches as a mono
          .subscribe(System.out::println);  // Can call mono operations
    }
    
    @Test
    void testFindPersonByIdNotFoundWithEmptyMono() {
        Flux<Person> personFlux = personRepository.findAll();
        final Integer id = 8;
        Mono<Person> personMono = personFlux
            .filter(person -> person.getId() == id)
            .next();

        personMono.subscribe(System.out::println);

        // Testing for an empty mono
        assertFalse(personMono.hasElement().block());
    }

    @Test
    void testFindPersonByIdNotFoundWithException() {
        Flux<Person> personFlux = personRepository.findAll();
        final Integer id = 8;
        Mono<Person> personMono = personFlux
            .filter(person -> person.getId() == id)
            .single()

            // Optional to handle the error  but will generate 2 error messages then
            .doOnError(throwable -> {
                System.out.println("Error occured in flux");
            });

        // Need to handle the error here also if this block is present
        // BUT if missing , there will not be any exception in the previous block
        //     since nothing was actually performed
        personMono.subscribe(
            person -> System.out.println(person.toString())
            ,throwable -> {
                System.err.println("Error occured in mono: "+throwable.toString());
            }
        );
    }

    @Test
    void testGetByIdStepVerifier() {
        Mono<Person>  personMono = personRepository.getById(3);
        StepVerifier.create(personMono).expectNextCount(1).verifyComplete();

        personMono.subscribe(person -> System.out.println(person.getFirstName()));
    }

    @Test
    void testGetByIdNotFoundStepVerifier() {
        Mono<Person>  personMono = personRepository.getById(6);
        StepVerifier.create(personMono).expectNextCount(0).verifyComplete();

        personMono.subscribe(System.out::println);
    }
}