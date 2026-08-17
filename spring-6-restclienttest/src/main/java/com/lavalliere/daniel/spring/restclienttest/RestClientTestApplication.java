package com.lavalliere.daniel.spring.restclienttest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@SpringBootApplication
public class RestClientTestApplication {

    @Bean
    public RestClient restClient(RestClient.Builder restClientBuilder) {
        return restClientBuilder.build();
    }

    @Bean
    public RestClientCustomizer globalRestClientCustomizer() {
        // The builder instance is provided by Spring Boot to this callback method
        return (restClientBuilder -> restClientBuilder
            .baseUrl("https://example.com")
            .defaultHeader("X-Custom-Header", "MyValue"));
    }

    public static void main(String[] args) {
        SpringApplication.run(RestClientTestApplication.class, args);
    }
}
