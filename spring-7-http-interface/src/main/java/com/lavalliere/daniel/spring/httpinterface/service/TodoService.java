package com.lavalliere.daniel.spring.httpinterface.service;

import com.lavalliere.daniel.spring.httpinterface.domain.Todo;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.service.annotation.*;

import java.io.IOException;
import java.util.List;

@HttpExchange(url="http://jsonplaceholder.typicode.com", accept="application/json")
public interface TodoService {

    @GetExchange("/todos")
    List<Todo> getAllTodos();

    // NOTE:  Requires @EnableResilientMethods in any @Configuration class managed by the applciation
    //        including, of course, the class annotated with @SpringBootApplication

    // @Retryable({ RestClientException.class, IOException.class })
    @Retryable(                               // NOTE: without args, it would retry for all exceptions
        includes = { RestClientException.class, IOException.class },  // Retry on HTTP/Client errors Throwable[] argument
        maxRetries = 3,                       // 3 retries not including 1st attempt
        // maxAttempts = 4,                   // No longer supported BUT included initial attempt to the maxRetries+
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
