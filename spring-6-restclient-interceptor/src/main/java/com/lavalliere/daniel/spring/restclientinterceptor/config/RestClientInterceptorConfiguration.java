package com.lavalliere.daniel.spring.restclientinterceptor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

// @Configuration
public class RestClientInterceptorConfiguration {

    @Bean
    public RestClient baseRestClient(RestClient.Builder builder, ClientHttpRequestInterceptor interceptor) {
        // Build one immutable shared client for the base URI
        return builder
            .baseUrl("http://jsonplaceholder.typicode.com")
            .defaultHeader("Accept", "application/json")
            .requestFactory(new JdkClientHttpRequestFactory())
            // .requestInterceptor(interceptor)
            .requestInterceptor(interceptor)
            .build();
    }
}
