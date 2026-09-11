package com.lavalliere.daniel.spring7aisample3;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.MemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Spring7AiSample3Application {

    @Bean
    public MemoryAdvisor  memoryAdvisor(ChatMemory chatMemory) {
        return MessageChatMemoryAdvisor.builder(chatMemory).build();
    }

    @Bean
    public ChatClient chatClient(
        ChatClient.Builder clientBuilder,
        MemoryAdvisor memoryAdvisor
    ) {
        return clientBuilder
            .defaultAdvisors(
                memoryAdvisor,
                new SimpleLoggerAdvisor()
            )  // log chat requests using the built-in SimpleLoggerAdvisor
            .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(Spring7AiSample3Application.class, args);
    }

}
