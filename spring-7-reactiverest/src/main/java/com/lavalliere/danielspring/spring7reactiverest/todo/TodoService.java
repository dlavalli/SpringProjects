package com.lavalliere.danielspring.spring7reactiverest.todo;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@HttpExchange(url="http://jsonplaceholder.typicode.com", accept="application/json")
public interface TodoService {

    @GetExchange("/todos")
    Flux<Todo> getAllTodos();

    @GetExchange("/todos/{id}")
    Mono<Todo> getTodoById(@PathVariable Long id);

    @GetExchange("/todos?userId={userId}")
    Mono<Todo> getTodoByUserId(@RequestParam Long userId);

    @PostExchange("/todos")
    Mono<Todo> createTodo(@RequestBody Todo todo);

    @PutExchange("/todos/{id}")
    Mono<Todo> updateTodo(@PathVariable Long id, @RequestBody Todo todo);

    @DeleteExchange("/todos/{id}")
    void deleteTodo(@PathVariable Long id);
}
