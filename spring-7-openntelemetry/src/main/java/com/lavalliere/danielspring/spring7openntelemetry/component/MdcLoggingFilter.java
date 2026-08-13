package com.lavalliere.danielspring.spring7openntelemetry.component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;


/*
 * A jakarta.servlet.Filter is the most reliable tool because it wraps the entire request execution.
 * It catches requests before they hit your controller and guarantees execution of the cleanup block
 * after the response is sent
 */

@Component
public class MdcLoggingFilter implements Filter  {
    private static final String TRACE_ID_KEY = "traceId";

    @Override
    public void doFilter(
        ServletRequest request,
        ServletResponse response,
        FilterChain chain
    ) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        try {
            // 1. Extract existing trace ID from headers or generate a new one
            String traceId = httpRequest.getHeader("X-Trace-Id");
            if (traceId == null || traceId.isBlank()) {
                traceId = UUID.randomUUID().toString();
            }

            // 2. Setup the MDC context before entering the rest of the application
            MDC.put(TRACE_ID_KEY, traceId);

            // Continue down the filter chain to your @RestController endpoints
            chain.doFilter(request, response);

        } finally {
            // 3. ALWAYS clear the MDC context in a finally block to prevent thread leaks
            MDC.clear();
        }
    }
}
