package com.lavalliere.danielspring.spring7webclient.todo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Service
public class TodoService {
    private final WebClient webClient;

    public Flux<Todo> getAllProducts() {
        return this.webClient.get()
            .uri("/products")
            .retrieve()
            .bodyToFlux(Todo.class);
    }

    /*
    @GetExchange("/todos")
    Flux<Todo> getAllTodos();

    @GetExchange("/todos/{id}")
    Mono<Todo> getTodoById(@PathVariable Long id);

    @GetExchange("/todos?userId={userId}")
    Mono<Todo> getTodoByUserId(@RequestParam Long userId);

//    @GetExchange("/todos")
//    Todo getTodoByUserId(@RequestParam Long userId);

    @PostExchange("/todos")
    Mono<Todo> createTodo(@RequestBody Todo todo);

    @PutExchange("/todos/{id}")
    Mono<Todo> updateTodo(@PathVariable Long id, @RequestBody Todo todo);

    @DeleteExchange("/todos/{id}")
    void deleteTodo(@PathVariable Long id);
     */
}
