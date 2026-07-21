package com.lavalliere.daniel.spring.elksample.config;

import org.jspecify.annotations.NullMarked;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.support.HttpHeaders;

// This is just an example configuration in case needed. Default using spring boot properties is sufficient
// Enable this to have this configuration use
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.lavalliere.daniel.spring.elksample")
public class ElkSampleConfiguration extends ElasticsearchConfiguration {


    private final String elasticsearchHostUrl;
    private final String elasticsearchClientUsername;
    private final String elasticsearchClientSecret;

    public ElkSampleConfiguration(
        @Value("${ElkSample.host.client_url}") String elasticsearchHostUrl,
        @Value("${ElkSample.host.client_username}") String elasticsearchClientUsername,
        @Value("${ElkSample.host.client_secret}") String elasticsearchClientSecret
        ) {
        this.elasticsearchHostUrl = elasticsearchHostUrl;
        this.elasticsearchClientSecret = elasticsearchClientSecret;
        this.elasticsearchClientUsername = elasticsearchClientUsername;
    }

    @NullMarked
    @Override
    public ClientConfiguration clientConfiguration() {
        // Inform the 9.x client to use 8.x request/response structures
        // BUT does not work since the headers are hardcoded at the transport level and these are for the client level
        // SO The Spring Boot 4.x version will enforce communication to elasticsearh 9.x
        // Unless you stop using the spring data elasticsearch and instead use the client directly and use an 87.x compatible version

        //HttpHeaders compatibilityHeaders = new HttpHeaders();
        //compatibilityHeaders.add("Accept", "application/vnd.elasticsearch+json;compatible-with=8");
        //compatibilityHeaders.add("Content-Type", "application/vnd.elasticsearch+json;compatible-with=8");

        ClientConfiguration config = ClientConfiguration.builder()
            .connectedTo(elasticsearchHostUrl)
            // .connectedToLocalhost()
            // .usingSsl()  // If used to enable TLS, Need to setup a local truststore with a correctly self-signed certificate and construct an SSLContext
            .withBasicAuth(elasticsearchClientUsername, elasticsearchClientSecret)
            // .withDefaultHeaders(compatibilityHeaders)
            .build();

        return config;
    }
}
