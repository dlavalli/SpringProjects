package com.lavalliere.daniel.spring.restclientinterceptor.components;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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

        logRequest(request, body);
        logHeaders(request.getHeaders());
        ClientHttpResponse response = execution.execute(request,body);
        return logResponse(request, response);

        // Return the response by forwarding the call
        // return execution.execute(request,body);
    }

    private void logHeaders(HttpHeaders  headers) {
        headers.forEach((name, values) -> values.forEach(value -> log.info("HTTP Header: {}={}", name, value)));
    }

    private void logRequest(HttpRequest request, byte[] body) {
        log.info("Request: {} {}", request.getMethod(), request.getURI());
        if (body.length > 0) {
            log.info("Request body: {}", new String(body, StandardCharsets.UTF_8));
        }
    }

    private ClientHttpResponse logResponse(HttpRequest request, ClientHttpResponse response) throws IOException {
        log.info("Response status: {}", response.getStatusCode());
        logHeaders(response.getHeaders());
        byte[] responseBody = response.getBody().readAllBytes();
        if (responseBody.length > 0) {
            log.info("Response body: {}", new String(responseBody, StandardCharsets.UTF_8));
        }
        return new BufferingClientHttpResponseWrapper(response, responseBody);
    }

    @RequiredArgsConstructor
    private static class BufferingClientHttpResponseWrapper implements ClientHttpResponse {

        private final ClientHttpResponse response;
        private final byte[] body;

        @Override
        public @NotNull HttpStatusCode getStatusCode() throws IOException {
            return response.getStatusCode();
        }

        @Override
        public @NotNull String getStatusText() throws IOException {
            return response.getStatusText();
        }

        @Override
        public void close() {
            response.close();
        }

        @Override
        public @NotNull InputStream getBody() throws IOException {
            return new ByteArrayInputStream(body);
        }

        @Override
        public @NotNull HttpHeaders getHeaders() {
            return response.getHeaders();
        }

        @Override
        // @Deprecated(since = "6.0", forRemoval = true)
        public int getRawStatusCode() throws IOException {
            return getStatusCode().value();
        }

    }
}
