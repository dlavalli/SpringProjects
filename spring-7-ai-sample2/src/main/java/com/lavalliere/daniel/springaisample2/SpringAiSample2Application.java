package com.lavalliere.daniel.springaisample2;

import com.lavalliere.daniel.springaisample2.service.AIChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringAiSample2Application {

    // https://docs.spring.io/spring-ai/docs/current/api/org/springframework/ai/chat/prompt/ChatOptions.html
    // https://docs.spring.io/spring-ai/docs/current/api/org/springframework/ai/chat/client/ChatClient.ChatClientRequestSpec.html

    /*
        Key Considerations
            Kebab-case: Spring Boot uses relaxed binding, so top-p is the standard format for properties files,
                        though topP will usually be map-bound correctly as well.
            Mutually Exclusive: OpenAI generally recommends adjusting either temperature or top-p,
                                but not both at the same time, as they both modify the token selection pool.

        //    @Bean
        //    public ChatOptions chatOptions() {
        //        return ChatOptions.builder()
        //            .model("gpt-5-mini")  // Can also be done from application.yaml/properties
        //            // .temperature(.99)
        //            .topP(.95)
        //            .build();
        //    }
    */
    @Bean
    public ChatOptions.Builder<?>    chatOptionsBuilder() {
        return ChatOptions.builder();
    }

    /*
       NOTE on Github Copilot:
       GitHub Copilot does not expose controls for temperature or topP to users, regardless of the underlying LLM selected
       (such as GPT-4o, Claude 3.5 Sonnet, or Gemini 1.5 Pro).
       The GitHub Copilot platform abstracts these low-level hyperparameters completely. Instead, it hardcodes specific values
       internally depending on the task type (e.g., forcing a low, highly deterministic temperature like 0.0 or 0.1
       for precise code generation, and slightly higher for general chat):
       Standard Models (GPT-4o, Claude, Gemini): GitHub Copilot calls these APIs using fixed, pre-configured parameters
       hidden behind its higher-level SDK.
       Reasoning/Thinking Models (GPT-5 family, o-series): If you route GitHub Copilot through custom endpoints
       (like Bring Your Own Key/BYOK), trying to inject a custom temperature will actually break the request. These newer
       reasoning models inherently reject custom temperatures (only accepting 1.0 or omitting the parameter entirely).
       If your workflow strictly requires adjusting temperature and topP for code generation, you will need to use alternative AI
       extensions like Aider, Cline, or IDEs like Zed and Cursor, which explicitly expose these LLM sampling settings to the user.

       NOTE on OpenAI:
       Almost all standard OpenAI text generation and chat models support both temperature and top_p parameters through their API.
       However, OpenAI recommends changing only one of these parameters per request rather than adjusting both at the same time.
       Models Supporting Temperature and Top-PGPT-4o and GPT-4o-miniGPT-4 Turbo and GPT-4GPT-3.5 TurboO-series reasoning models
       (such as o1 or o3-mini, though OpenAI restricts or fixes specific sampling parameter configurations on specialized reasoning
       tokens/modes)Key Parameter RulesTemperature: Controls the randomness of the model output. Higher values (like 0.8) make output
       more creative or random, while lower values (like 0.2) make it more focused and deterministic.
       Top-P (Nucleus Sampling): An alternative way to control randomness by sampling from a cumulative probability percentage
       (e.g., 0.1 means consider only tokens comprising the top 10% probability mass).
       Best Practice: Pick either temperature or top_p to modify, and leave the other at its default value
       (1.0) to prevent unpredictable compounding effects on text generation.
     */
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
                .defaultSystem(AIChatService.systemTemplate) // Set the LLM model's system behavior
                .build();
        }

    public static void main(String[] args) {
        SpringApplication.run(SpringAiSample2Application.class, args);
    }

}
