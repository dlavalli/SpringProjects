package com.lavalliere.daniel.spring.httpinterface.service;

import com.lavalliere.daniel.spring.httpinterface.domain.Todo;
import org.jspecify.annotations.Nullable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.retry.RetryException;
import org.springframework.core.retry.RetryListener;
import org.springframework.core.retry.RetryPolicy;
import org.springframework.core.retry.RetryTemplate;
import org.springframework.resilience.annotation.ConcurrencyLimit;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

//@Service
public class TraditionalTodoService {
    private final RestClient restClient;
    private final RetryTemplate retryTemplate;

    // NOTE:  In Spring Boot, a RestClient instance is not auto-configured as a bean,
    //        meaning you cannot directly autowire a RestClient into your constructor.
    //        The standard best practice is to inject the RestClient.Builder into your service
    //        component's constructor and compile the client immediately during bean initialization
    public TraditionalTodoService(
        RestClient.Builder restClientBuilder,
        RetryListener retryListener
        // , RetryTemplate retryTemplate
    ) {
        this.restClient = RestClient.builder().baseUrl("http://jsonplaceholder.typicode.com/").build();
        // this.retryTemplate = retryTemplate;

        // 1- Implicitly uses RetryPolicy.withDefault()  // 3 max attempts and will catch any exceptions
        //                                               // Same as @Autowired version
        // this.retryTemplate = new RetryTemplate();

        // 2- You can have more control of the parameters for the retry
        //    This also enables you to register a listener
         var retryPolicy = RetryPolicy
             .builder()
             .maxRetries(3)
             .delay(Duration.ofMillis(2000))
             .multiplier(2.0)
             .includes(List.of(RestClientException.class, IOException.class))
             .build();
         this.retryTemplate = new RetryTemplate(retryPolicy);
         this.retryTemplate.setRetryListener(retryListener);


    }

    public @Nullable Object bogusMethod() throws RetryException {
        final AtomicInteger attempt = new AtomicInteger(0);
        return retryTemplate.execute(() -> {   // ie takes a Retryable as argument and returns something
                                               //    public <R extends @Nullable Object> R execute(Retryable<R> retryable) throws RetryException
            int currentAttempt = attempt.incrementAndGet();

            // Do something here that will be repeated according to the RetryPolicy that was set
            return null;
        });
    }

    @ConcurrencyLimit(3)  // -1 = no limit, 1 = no  concurrent requests, n = Max n concurrent requests
    // @ConcurrencyLimit(value = 3, policy = ConcurrencyLimit.ThrottlePolicy.REJECT)
    private @Nullable Object notificationMethod() throws RetryException {
        final AtomicInteger attempt = new AtomicInteger(0);
        return retryTemplate.execute(() -> {   // ie takes a Retryable as argument and returns something
            //    public <R extends @Nullable Object> R execute(Retryable<R> retryable) throws RetryException
            int currentAttempt = attempt.incrementAndGet();

            // Do something here that will be repeated according to the RetryPolicy that was set
            return null;
        });
    }

    public List<Todo> getAllTodos() {
        return restClient.get().
            uri("/todos")
            .retrieve()
            .body(new ParameterizedTypeReference<List<Todo>>() {});
    }

    // Etc. for other boilerplate
}
