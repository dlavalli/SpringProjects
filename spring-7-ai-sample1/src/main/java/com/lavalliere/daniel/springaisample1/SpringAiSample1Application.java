package com.lavalliere.daniel.springaisample1;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringAiSample1Application {

    @Bean
    public ChatOptions.Builder<?> chatOptionsBuilder() {
        return ChatOptions.builder();
    }

    // https://docs.spring.io/spring-ai/docs/current/api/org/springframework/ai/chat/prompt/ChatOptions.html
    // https://docs.spring.io/spring-ai/docs/current/api/org/springframework/ai/chat/client/ChatClient.ChatClientRequestSpec.html
    @Bean
    public ChatClient chatClient(
        ChatClient.Builder clientBuilder,
        ChatOptions.Builder<?> chatOptionsBuilder
    ) {
        return clientBuilder
            .defaultOptions(
                chatOptionsBuilder
                    .model("gpt-5-mini")  // Can also be done from application.yaml/properties
                    // .temperature(1.0)  // Only 1 supported for this model
                    // .topP(.95)  // Not supported for model
                    // .topK(40)
            )
            .defaultAdvisors(new SimpleLoggerAdvisor())  // log chat requests using the built-in SimpleLoggerAdvisor
            // .defaultSystem(systemTemplate)
            .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringAiSample1Application.class, args);
    }

}
