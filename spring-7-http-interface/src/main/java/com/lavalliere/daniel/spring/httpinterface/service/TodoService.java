package com.lavalliere.daniel.spring.httpinterface.service;

import com.lavalliere.daniel.spring.httpinterface.domain.Todo;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.service.annotation.*;

import java.util.List;

@HttpExchange(url="http://jsonplaceholder.typicode.com", accept="application/json")
public interface TodoService {

    @GetExchange("/todos")
    List<Todo> getAllTodos();

    @Retryable(
        includes = RestClientException.class, // Retry on HTTP/Client errors
        maxRetries = 3,                       // 1 initial call + 3 retries = 4 total attempts
        delay = 1000,                         // Delay in milliseconds
        multiplier = 2                        // On first attempt, it will be 1sec delay, then 2 sec, then 4sec  etc
                                              // on configuration class, @EnableResilientMethods // Required to activate core @Retryable
    )
    @GetExchange("/todos/{id}")
    Todo getTodoById(@PathVariable Long id);

    @GetExchange("/todos?userId={userId}")
    Todo getTodoByUserId(@PathVariable Long userId);

//    @GetExchange("/todos")
//    Todo getTodoByUserId(@RequestParam Long userId);

    @PostExchange("/todos")
    Todo createTodo(@RequestBody Todo todo);

    @PutExchange("/todos/{id}")
    Todo updateTodo(@PathVariable Long id, @RequestBody Todo todo);

    @DeleteExchange("/todos/{id}")
    void deleteTodo(@PathVariable Long id);
}
