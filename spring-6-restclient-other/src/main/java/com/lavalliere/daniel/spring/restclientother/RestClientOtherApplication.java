package com.lavalliere.daniel.spring.restclientother;

import com.lavalliere.daniel.spring.restclientother.service.JsonPlaceholderService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@SpringBootApplication
public class RestClientOtherApplication {

    @Bean
    JsonPlaceholderService jsonPlaceholderService() {
        RestClient restClient = RestClient.create("http://jsonplaceholder.typicode.com");
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
            .builderFor(RestClientAdapter.create(restClient))
            .build();
        return httpServiceProxyFactory.createClient(JsonPlaceholderService.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(RestClientOtherApplication.class, args);
    }
}
