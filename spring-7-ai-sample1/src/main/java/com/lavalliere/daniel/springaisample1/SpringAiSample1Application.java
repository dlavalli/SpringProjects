package com.lavalliere.daniel.springaisample1;

import com.openai.services.blocking.ChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringAiSample1Application {

    @Bean
    public ChatClient chatClient(ChatClient.Builder clientBuilder) {
        return clientBuilder
            .defaultAdvisors(new SimpleLoggerAdvisor())  // log chat requests using the built-in SimpleLoggerAdvisor
            .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringAiSample1Application.class, args);
    }

}
