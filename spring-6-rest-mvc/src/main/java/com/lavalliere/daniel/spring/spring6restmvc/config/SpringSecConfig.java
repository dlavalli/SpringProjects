package com.lavalliere.daniel.spring.spring6restmvc.config;

import com.lavalliere.daniel.spring.spring6restmvc.domain.Customer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // Deprecated, replaced with https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/builders/HttpSecurity.html#authorizeHttpRequests(org.springframework.security.config.Customizer)
        http.authorizeHttpRequests()
            // .requestMatchers("/api-docs**","/swagger-ui/**").permitAll()   // CANNOT USE IF DEFINED override in application.properties
            .anyRequest().authenticated()
            .and().httpBasic(Customizer.withDefaults())
            .csrf(httpSecurityCsrfConfigurer -> {
            // Better to disable for specific api then disable for all
            httpSecurityCsrfConfigurer.ignoringRequestMatchers("/api/**");
        });

        return http.build();
    }

}