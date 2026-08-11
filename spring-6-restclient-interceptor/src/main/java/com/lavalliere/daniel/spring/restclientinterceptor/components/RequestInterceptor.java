package com.lavalliere.daniel.spring.restclientinterceptor.components;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
public class RequestInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public @NotNull ClientHttpResponse intercept(
        HttpRequest request,
        byte @NotNull [] body,   // Notice the ordering is important here so that @NotNull apply to the array itself
        ClientHttpRequestExecution execution
    ) throws IOException {
        log.info("Global Intercepting request: {}", request.getURI());

        // Just an example. You would not use this just to update headers normally
        request.getHeaders().add("X-Request-ID", UUID.randomUUID().toString());

        // Return the response by forwarding the call
        return execution.execute(request,body);
    }
}
