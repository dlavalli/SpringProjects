package com.lavalliere.daniel.spring.restclienttest.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lavalliere.daniel.spring.restclienttest.domain.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

// A test class annotation for Spring RestClient that focus only on beans using RestTemplateBuilder or RestClient.Builder
// This does NOT load the full context. It also auto configure a mock rest service server
@RestClientTest(PostService.class)
class PostServiceTest {

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private PostService service;

    @Autowired
    private ObjectMapper objectMapper;

    private List<Post> testData;

    @BeforeEach
    void setUp() {
        testData = List.of(
            Post.builder().userId(1L).id(1L).title("Hello, world!").body("This is my first post!").build(),
            Post.builder().userId(2L).id(2L).title("Testing RestClient wit @RestClientTest").body("This is another post").build()
        );
    }

    @Test
    void shouldFindAllPosts() throws JsonProcessingException {
        // Given  testData

        // When
        server
            .expect(requestTo("http://jsonplaceholder.typicode.com/posts"))
            .andRespond(
                withSuccess(
                    objectMapper.writeValueAsString(testData),
                    MediaType.APPLICATION_JSON
                )
            );

        // Then
        List<Post> response = service.findAll();
        assertNotNull(response);
        assertEquals(2, response.size());
        server.verify();
    }

//    @Test
//    void findById() {
//    }
//
//    @Test
//    void create() {
//    }
//
//    @Test
//    void update() {
//    }
//
//    @Test
//    void delete() {
//    }
}