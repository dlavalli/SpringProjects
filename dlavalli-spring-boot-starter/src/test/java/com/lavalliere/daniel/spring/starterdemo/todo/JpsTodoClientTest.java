package com.lavalliere.daniel.spring.starterdemo.todo;

import com.lavalliere.daniel.spring.starterdemo.JsonPlaceHolderServiceConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.web.client.RestClientAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

// @ExtendWith(SpringExtension.class)
class JpsTodoClientTest {
    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
        .withConfiguration(AutoConfigurations.of(
            JsonPlaceHolderServiceConfiguration.class,
            RestClientAutoConfiguration.class)
        );

    @Test
    void shouldContainTodoRestClientBean() {
        contextRunner.run(context -> {
            assertTrue(context.containsBean("jsonPlaceHolderRestClient"));
            assertTrue(context.containsBean("jspTodoClient"));
        });
    }
}