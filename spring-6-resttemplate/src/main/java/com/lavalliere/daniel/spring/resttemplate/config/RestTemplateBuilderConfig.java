package com.lavalliere.daniel.spring.resttemplate.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.client.RestTemplateBuilderConfigurer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.DefaultUriBuilderFactory;


@Configuration
public class RestTemplateBuilderConfig {

    @Value("${rest.template.rootUrl}")
    String rootUrl;

    // To simplify the configuration of RestTemplateBuilder, use RestTemplateBuilderConfigurer
    @Bean
    RestTemplateBuilder restTemplateBuilder(RestTemplateBuilderConfigurer configurer){

        // Create the builder and configure a new instance
        // Configurer takes the new instance and configures it with the spring boot defaults
        RestTemplateBuilder builder = configurer.configure(new RestTemplateBuilder());

        // Set the default basepath for the api calls
        DefaultUriBuilderFactory uriBuilderFactory = new
            DefaultUriBuilderFactory(rootUrl);

        // uriTemplateHandler returns a new instance of the resttemplate
        // so if you just call uriTemplateHandler and return the builder
        // it wont be configured so need to setup this way
        return builder.uriTemplateHandler(uriBuilderFactory);
    }
}