package com.lavalliere.daniel.spring.httpinterface.service;

import com.lavalliere.daniel.spring.httpinterface.domain.Todo;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

//@Service
public class TraditionalTodoService {
    private final RestClient restClient;

    // NOTE:  In Spring Boot, a RestClient instance is not auto-configured as a bean,
    //        meaning you cannot directly autowire a RestClient into your constructor.
    //        The standard best practice is to inject the RestClient.Builder into your service
    //        component's constructor and compile the client immediately during bean initialization
    public TraditionalTodoService(RestClient.Builder restClientBuilder) {
        this.restClient = RestClient.builder().baseUrl("http://jsonplaceholder.typicode.com/").build();
    }

    public List<Todo> getAllTodos() {
        return restClient.get().
            uri("/todos")
            .retrieve()
            .body(new ParameterizedTypeReference<List<Todo>>() {});
    }

    // Etc. for other boilerplate
}
