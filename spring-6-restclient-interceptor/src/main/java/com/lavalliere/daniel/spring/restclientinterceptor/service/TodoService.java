package com.lavalliere.daniel.spring.restclientinterceptor.service;

import com.lavalliere.daniel.spring.restclientinterceptor.domain.Todo;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class TodoService {

    private final RestClient restClient;

    public TodoService(RestClient.Builder restClientBuilder, ClientHttpRequestInterceptor interceptor) {
        this.restClient = restClientBuilder
            .baseUrl("http://jsonplaceholder.typicode.com")
            .defaultHeader("X-Service-Type", "Todos")
            .requestFactory(new JdkClientHttpRequestFactory())
            .requestInterceptor(interceptor)
            .build();
    }

    // NOTE : That you can also use HTTP Interface to avoid all this boiler plate

    public List<Todo> findAll() {
        return restClient
            .get().uri("/todos")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .body(new ParameterizedTypeReference<List<Todo>>() {});
    }

    /*
        @GetExchange("/todos")
    List<Todo> getAllTodos();

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
     */

}
