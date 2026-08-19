package com.lavalliere.daniel.spring.restclient.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.client.OAuth2ClientHttpRequestInterceptor;
import org.springframework.web.client.RestClient;


@Configuration
public class RestClientBuilderConfig {

    @Value("${rest.template.rootUrl}")
    String rootUrl;

    @Bean
    OAuth2AuthorizedClientManager auth2AuthorizedClientManager(
        ClientRegistrationRepository clientRegistrationRepository,
        OAuth2AuthorizedClientService oAuth2AuthorizedClientService
    ) {
        var authorizedClientProvider = OAuth2AuthorizedClientProviderBuilder.builder()
            .clientCredentials()
            .build();

        var authorizedClientManager = new AuthorizedClientServiceOAuth2AuthorizedClientManager(
            clientRegistrationRepository,
            oAuth2AuthorizedClientService
        );
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
        return authorizedClientManager;
    }


    @Bean
    public RestClient externalApiRestClient(
        RestClient.Builder builder,
        OAuth2AuthorizedClientManager authorizedClientManager) {

        // Instantiate the Spring Security interceptor for RestClient
        OAuth2ClientHttpRequestInterceptor oauth2Interceptor =
            new OAuth2ClientHttpRequestInterceptor(authorizedClientManager);

        // Dynamically resolve the client registration ID for outbound requests
        oauth2Interceptor.setClientRegistrationIdResolver(request -> "springauth");

        // Build and return the pre-configured RestClient
        return builder
            .baseUrl(rootUrl)
            .requestInterceptor(oauth2Interceptor)
            .build();
    }
}