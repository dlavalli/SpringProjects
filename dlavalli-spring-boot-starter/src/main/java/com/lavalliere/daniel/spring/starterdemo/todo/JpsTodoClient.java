package com.lavalliere.daniel.spring.starterdemo.todo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

public class JpsTodoClient {
    private static final Logger logger = LoggerFactory.getLogger(JpsTodoClient.class);
    private final RestClient restClient;

    public JpsTodoClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public String getName() {
        return "JpsTodoClient";
    }

    public List<Todo> findAll() {
        return restClient.get()
            .uri("/todos")
            .retrieve()
            .body(new ParameterizedTypeReference<>(){});
    }

    public Todo findById(Integer id) {
        return restClient.get()
            .uri("/todos/{id}", id)
            .retrieve()
            .body(Todo.class);
    }
}
