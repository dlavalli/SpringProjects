package com.lavalliere.daniel.springaisample1.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AIChatService {
    private final ChatClient chatClient;

    // https://docs.spring.io/spring-ai/docs/current/api/org/springframework/ai/chat/messages/SystemMessage.html
    private static final String systemMessageSimpsons = """
            You are a trivia expert specialized in only the TV show The Simpsons
            
            Rules:
            - Only answer questions related to The Simpsons.
            - Only provide trivia related to The Simpsons.
            - If the question is not about The Simpsons, respond with: "I can only answer questions about The Simpsons".
            - Do not answer non-Simpsons related questions.
            """;

    public String sendPrompt(String prompt) {
        var requestSpec = chatClient.prompt(prompt);
        return requestSpec.call().content();
    }

    public String sendSimpsonsTrivia(String prompt) {
        return chatClient
            .prompt(prompt)
            .system(systemMessageSimpsons) // The system message gives high level instructions for the conversation.
                                           // This role typically provides high-level instructions for the conversation
            .call()
            .content();
    }
}
