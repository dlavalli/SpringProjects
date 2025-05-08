package com.lavalliere.daniel.spring.spring6restmvc.components;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@Profile("springHttpLog")
@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public boolean preHandle(
        HttpServletRequest request,
        HttpServletResponse response, Object handler
    ) {
        StringBuilder requestLog = new StringBuilder();
        requestLog.append("Request: ").append(request.getMethod()).append(" ").append(request.getRequestURI());

        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames.hasMoreElements()) {
            requestLog.append("\nHeaders: ");
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                requestLog.append(headerName).append(": ").append(request.getHeader(headerName)).append(", ");
            }
        }

        if ("POST".equalsIgnoreCase(request.getMethod()) || "PUT".equalsIgnoreCase(request.getMethod())) {
            // Add logic to read request body if needed (consider performance and security)
        }

        logger.info(requestLog.toString());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        StringBuilder responseLog = new StringBuilder();
        responseLog.append("Response: ").append(response.getStatus());

        // Add logic to read response body if needed (consider performance and security)

        logger.info(responseLog.toString());
    }
}
