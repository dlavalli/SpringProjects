package com.lavalliere.daniel.spring.httpinterface.service;

import com.lavalliere.daniel.spring.httpinterface.config.HttpClientConfig;
import com.lavalliere.daniel.spring.httpinterface.domain.Todo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.restclient.test.autoconfigure.RestClientTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(TodoService.class)
@Import(TodoServiceTest.TodoServiceTestConfiguration.class)
class TodoServiceTest {
    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private TodoService service;

    @Autowired
    private ObjectMapper objectMapper;

    private List<Todo> testData;

    // Required else no instance of the TodoService is actually bound
    @TestConfiguration
    static class TodoServiceTestConfiguration {
        @Bean
        TodoService todoService(RestClient.Builder restClientBuilder ) {
            RestClient client =  restClientBuilder.baseUrl("http://jsonplaceholder.typicode.com").build();
            RestClientAdapter restClientAdapter = RestClientAdapter.create(client);
            HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
            return httpServiceProxyFactory.createClient(TodoService.class);
        }
    }

    @BeforeEach
    void setUp() {
        testData = List.of(
            // Long id, Long userId, String title, Boolean completed
            Todo.builder().userId(1L).id(1L).title("First test todo").completed(true).build(),
            Todo.builder().userId(2L).id(2L).title("Second test todo").completed(false).build()
        );
    }

    @Test
    void getAllTodos() {
        // Given  testData

        // When
        server
            .expect(requestTo("http://jsonplaceholder.typicode.com/todos"))
            .andRespond(
                withSuccess(
                    objectMapper.writeValueAsString(testData),
                    MediaType.APPLICATION_JSON
                )
            );

        // Then
        List<Todo> response = service.getAllTodos();
        assertNotNull(response);
        assertEquals(2, response.size());
        server.verify();
    }
}