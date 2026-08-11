package com.lavalliere.daniel.spring.restclientinterceptor;

import com.lavalliere.daniel.spring.restclientinterceptor.service.TodoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@SpringBootApplication
public class RestClientInterceptorApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestClientInterceptorApplication.class, args);
    }
}
