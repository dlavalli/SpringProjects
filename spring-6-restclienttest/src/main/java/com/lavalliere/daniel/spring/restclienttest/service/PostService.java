package com.lavalliere.daniel.spring.restclienttest.service;

import com.lavalliere.daniel.spring.restclienttest.domain.Post;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

// Replaced with JsonPlaceholderService
@Service
public class PostService {

    private final RestClient restClient;

    public PostService(RestClient.Builder restClientBuilder) {
        // JdkClientHttpRequestFactory factory = new JdkClientHttpRequestFactory();    // When used. The unit test ignore the mock server and actually perform a call to the remote host
                                                                                       // which will either end up loading to many results or will be blocked
        this.restClient = restClientBuilder
            .baseUrl("http://jsonplaceholder.typicode.com")
            // .requestFactory(factory)
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
