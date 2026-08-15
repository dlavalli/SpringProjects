package com.lavalliere.danielspring.spring7reactiverest.todo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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

    /*
     * Important NOTE:
     * What would be the consequence of changing MediaType.TEXT_EVENT_STREAM to MediaType.APPLICATION_JSON in this case ?
     *
     * With APPLICATION_JSON, Spring/WebFlux will typically serialize
     * the Flux<Todo> as one JSON array (e.g. [ {...}, {...} ]) and the client usually gets it when completed, not as incremental SSE events.
     * So consequences:
     *
     *   TEXT_EVENT_STREAM
     *       Response is SSE stream (text/event-stream)
     *       Items can arrive one-by-one continuously
     *       Good for live updates / long-lived connections
     *
     *   APPLICATION_JSON
     *       Response is normal JSON (application/json)
     *       Usually buffered/aggregated as a finite payload (often an array)
     *       Better for “get all once” use cases, not live push
     *
     */

    public Flux<Todo> findAll() {
        return this.webClient
            .get()
            .uri("/todos")
            .accept(MediaType.APPLICATION_JSON)  // MediaType.TEXT_EVENT_STREAM
            .retrieve()
            .bodyToFlux(Todo.class);
    }

    public Mono<Todo> findById() {
        return this.webClient
            .get()
            .uri("/todos")
            .accept(MediaType.APPLICATION_JSON)// MediaType.TEXT_EVENT_STREAM
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
