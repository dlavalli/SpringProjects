package com.lavalliere.daniel.spring.spring6restmvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecConfig {

    @Bean
    @Order(1)
    public SecurityFilterChain actuatorSecurityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher(EndpointRequest.toAnyEndpoint())
            .authorizeHttpRequests(authorize ->
                authorize.anyRequest().permitAll()
            );

        return http.build();
    }

    /*
    // Other possible way to enable security

    @Bean
    @Order(1)
    public SecurityFilterChain actuatorSecurityFilterChain(HttpSecurity http) throws Exception {
        // Enable any endpoint we enabled and allow no login
        http.authorizeHttpRequests(authorize ->
            authorize.requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll());

        return http.build();
    }
     */

}
