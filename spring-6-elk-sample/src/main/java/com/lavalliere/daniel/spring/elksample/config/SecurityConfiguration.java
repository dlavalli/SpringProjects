package com.lavalliere.daniel.spring.elksample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF if dealing with stateless REST APIs
            .authorizeHttpRequests(auth -> auth
                // Permitting ALL GET requests on the person API endpoint
                // .requestMatchers(HttpMethod.GET, "/api/{version}/person/**").permitAll() // NOT just /**
                // .requestMatchers(HttpMethod.GET, "/api/{version}/{id}").permitAll() // NOT just /**

                // All other non-GET requests (POST, PUT, DELETE) will still require authentication
                // .anyRequest().authenticated()

                .anyRequest().permitAll()  // Just for testing purpose obviously !!!
            );
            // .httpBasic(Customizer.withDefaults()); //  enables Basic Authentication for request

        return http.build();
    }
}
