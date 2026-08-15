package com.lavalliere.danielspring.spring7reactiverest.todo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// @Component
public class TodoBaseClient {
    private final WebClient webClient;
    public TodoBaseClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<Todo> findAll() {
        return this.webClient
            .get()
            .uri("/todos")
            .retrieve()
            .bodyToFlux(Todo.class);
    }

    public Mono<Todo> findById() {
        return this.webClient
            .get()
            .uri("/todos")
            .retrieve()
            .bodyToMono(Todo.class);
    }

    /*
     @Override
    public Flux<User> getUsers(Mono<Integer> limit) {
        return WebClient.create(api_url)
                .get()
                .uri(uriBuilder -> uriBuilder.queryParam("limit", limit.block()).build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .flatMap(resp -> resp.bodyToMono(UserData.class))
                .flatMapIterable(UserData::getData);
    }
     */
}
