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

    // https://docs.spring.io/spring-ai/docs/current/api/org/springframework/ai/chat/client/ChatClient.ChatClientRequestSpec.html
    // https://docs.spring.io/spring-ai/docs/current/api/org/springframework/ai/chat/messages/SystemMessage.html
    private static final String systemMessageSimpsons = """
            You are a trivia expert specialized in only the TV show The Simpsons
            
            Rules:
            - Only answer questions related to The Simpsons.
            - Only provide trivia related to The Simpsons.
            - If the question is not about The Simpsons, respond with: "I can only answer questions about The Simpsons".
            - Do not answer non-Simpsons related questions.
            """;

    // Alternately could use a PromptTemplate if we want to generate several alternate versions of the system message
    // https://docs.spring.io/spring-ai/docs/current/api/org/springframework/ai/chat/prompt/PromptTemplate.html
    // https://docs.spring.io/spring-ai/docs/current/api/org/springframework/ai/chat/client/ChatClient.ChatClientRequestSpec.html
    /*
       String userTemplate = """
       ORIGINAL_TWEET:
       {postText}

       TOPIC_HINT:
       {topicInt}

       EMOJI_LEVEL:
       {emojiLevel}

       MODERNIZATION_LEVEL:
       {modernizationLevel}
       """;

       PromptTemplate promptTemplate = new PromptTemplate(userTemplate);
       String userPrompt = promptTemplate.render(Map.of(
         "postText", originalTweet,
         "topicInt", topicInt,
         "emojiLevel", emojiLevel,
         "modernizationLevel", modernizationLevel
       ));

       // In this example you  are setting the User message part of the prompt
       return chatClient().prompt().system(systemMessage).user(userPrompt).call().content();
     */


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
