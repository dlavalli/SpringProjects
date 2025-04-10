package com.lavalliere.daniel.spring;

import com.lavalliere.daniel.spring.starterdemo.todo.JpsTodoClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
public class CustomStarterClient {

    @Autowired
    public void demo(RestClient restClient) {
        var todoClient = new JpsTodoClient(restClient);
        log.info(todoClient.getName());
    }
}
