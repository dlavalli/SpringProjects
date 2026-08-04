package com.lavalliere.daniel.spring.elksample.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lavalliere.daniel.spring.elksample.component.HttpTrafficLoggingFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class FilterConfig {

    private final ObjectMapper objectMapper;

    @Bean
    public FilterRegistrationBean<HttpTrafficLoggingFilter> loggingFilterRegistration() {
        FilterRegistrationBean<HttpTrafficLoggingFilter> registrationBean = new FilterRegistrationBean<>();

        // Set the custom logging filter instance
        registrationBean.setFilter(new HttpTrafficLoggingFilter(objectMapper));

        // Intercept all incoming application HTTP requests, ignore everything else
        registrationBean.addUrlPatterns("/person");

        // Set the order of execution
        // Set execution order (Ordered.LOWEST_PRECEDENCE (ie: Integer.MIN_VALUE) ensures it runs early/late in the chain)
        // Set execution order (Ordered.HIGHEST_PRECEDENCE (ie: Integer.MAX_VALUE) runs it first)
        registrationBean.setOrder(1);

        return registrationBean;
    }
}
