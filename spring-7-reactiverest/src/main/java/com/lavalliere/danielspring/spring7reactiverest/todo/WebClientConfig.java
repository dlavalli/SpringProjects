package com.lavalliere.danielspring.spring7reactiverest.todo;

import io.netty.channel.ChannelOption;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
// import org.springframework.web.service.registry.ImportHttpServices;

// @ImportHttpServices(TodoService.class)
@Configuration(proxyBeanMethods = false)
public class WebClientConfig {

    @Bean
    WebClient secureWebClient() {
        String baseUrl = "http://jsonplaceholder.typicode.com"; // use https, not http for secure client

        DefaultUriBuilderFactory uriFactory = new DefaultUriBuilderFactory(baseUrl);
        uriFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.TEMPLATE_AND_VALUES);

        HttpClient httpClient = HttpClient.create()
            //.secure(ssl -> ssl -> ssl.sslContext(Http11SslContextSpec.forClient())
            .responseTimeout(Duration.ofSeconds(10))
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);

        return WebClient.builder()
            .uriBuilderFactory(uriFactory)
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .filter(logRequest())         // optional
            .filter(logResponse())        // optional
            // .filter(oauth2ExchangeFilter()) // if using OAuth2
            .build();
    }

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(Mono::just);
    }

    private ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(Mono::just);
    }

    @Bean
    TodoService todoService(WebClient webClient) {
        HttpServiceProxyFactory factory =
            HttpServiceProxyFactory.builderFor(WebClientAdapter.create(webClient)).build();

        return factory.createClient(TodoService.class);
    }
}
