package com.lavalliere.daniel.spring.starterdemo;

import com.lavalliere.daniel.spring.starterdemo.todo.JpsTodoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

// NOTE since this does not have a main application (ie: static main)
// so address this in resources META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports
// and specify the main class there
@AutoConfiguration
@EnableConfigurationProperties(JsonPlaceHolderServiceProperties.class)
public class JsonPlaceHolderServiceConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(JsonPlaceHolderServiceConfiguration.class);
    private final JsonPlaceHolderServiceProperties jspProperties;

    public JsonPlaceHolderServiceConfiguration(JsonPlaceHolderServiceProperties jspProperties) {
        this.jspProperties = jspProperties;
    }

    @Bean("jsonPlaceHolderRestClient")
    RestClient restClient(RestClient.Builder builder) {
        return builder.baseUrl(jspProperties.baseUrl()).build();
    }

    @Bean("jspTodoClient")
    JpsTodoClient jpsTodoClient(RestClient restClient) {
        return new JpsTodoClient(restClient);
    }
}
