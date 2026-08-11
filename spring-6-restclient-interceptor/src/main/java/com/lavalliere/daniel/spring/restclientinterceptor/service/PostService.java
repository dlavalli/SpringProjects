package com.lavalliere.daniel.spring.restclientinterceptor.service;

import com.lavalliere.daniel.spring.restclientinterceptor.domain.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class PostService {

    private final RestClient restClient;

    public PostService(RestClient.Builder restClientBuilder, ClientHttpRequestInterceptor interceptor) {
        this.restClient = restClientBuilder
            .baseUrl("http://jsonplaceholder.typicode.com")
            .defaultHeader("X-Service-Type", "Posts")
            .requestFactory(new JdkClientHttpRequestFactory())
            // .requestInterceptor(interceptor)
            .requestInterceptor(((request, body, execution) -> {  // Alternate possible implementation of interceptor
                log.info("Local Intercepting request: {}", request.getURI());

                // Just an example. You would not use this just to update headers normally
                request.getHeaders().add("X-Request-ID", UUID.randomUUID().toString());

                // Return the response by forwarding the call
                return execution.execute(request,body);
            }))
            .build();
    }


    // NOTE : That you can also use HTTP Interface to avoid all this boiler plate

    public List<Post> findAll() {
        return restClient
            .get().uri("/posts")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .body(new ParameterizedTypeReference<List<Post>>() {});
    }

    public Post findById(Long id) {
        return restClient
            .get().uri("/posts/{id}", id)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .body(Post.class);
    }

    public Post create(Post post) {
        return restClient.post().uri("/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .body(post)
            .retrieve()
            .body(Post.class);
    }

    public Post update(Long id, Post post) {
        return restClient.put().uri("/posts/{id}", id)
            .contentType(MediaType.APPLICATION_JSON)
            .body(post)
            .retrieve()
            .body(Post.class);
    }

    public void delete(Long id) {
        restClient.delete()
            .uri("/posts/{id}", id)
            .retrieve()
            .toBodilessEntity();  // To avoid returning a body
    }
}
