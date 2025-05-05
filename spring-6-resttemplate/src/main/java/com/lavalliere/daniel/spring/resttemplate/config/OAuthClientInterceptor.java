package com.lavalliere.daniel.spring.resttemplate.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import static java.util.Objects.isNull;

@Component
public class OAuthClientInterceptor implements ClientHttpRequestInterceptor {
    private final OAuth2AuthorizedClientManager manager;
    // private final Authentication principal;  //  ???  not used
    private final ClientRegistration clientRegistration;

    public OAuthClientInterceptor(OAuth2AuthorizedClientManager manager, ClientRegistrationRepository clientRegistrationRepository) {
        this.manager = manager;
        // this.principal = createPrincipal();
        this.clientRegistration = clientRegistrationRepository.findByRegistrationId("springauth"); // from application.properties
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        OAuth2AuthorizeRequest oAuth2AuthorizeRequest = OAuth2AuthorizeRequest
            .withClientRegistrationId(clientRegistration.getRegistrationId())
            .principal(createPrincipal())
            .build();

        OAuth2AuthorizedClient client = manager.authorize(oAuth2AuthorizeRequest);

        if (isNull(client)) {
            throw new IllegalStateException("Missing credentials");
        }

        // Adding the authorization token to the http header
        request.getHeaders().add(
            HttpHeaders.AUTHORIZATION,
            "Bearer " + client.getAccessToken().getTokenValue());

        return execution.execute(request, body);
    }

    /*
       In the context of OAuth 2.0, a "principal" refers to the user, service, or application
       that is requesting access to protected resources. It's the entity whose identity is being asserted
       when the client (the application) requests access. OAuth 2.0 uses access tokens to represent the authorization
       of a principal to access resources.
     */
    private Authentication createPrincipal() {
        return new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() { return Collections.emptySet(); }

            @Override
            public Object getCredentials() { return null; }

            @Override
            public Object getDetails() { return null; }

            @Override
            public Object getPrincipal() { return this; }

            @Override
            public boolean isAuthenticated() { return false; }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException { }

            @Override
            public String getName() { return clientRegistration.getClientId(); }
        };
    }
}