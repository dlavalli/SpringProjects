package com.lavalliere.daniel.spring.springaifunctions.services;

import com.lavalliere.daniel.spring.springaifunctions.functions.WeatherServiceFunction;
import com.lavalliere.daniel.spring.springaifunctions.model.Answer;
import com.lavalliere.daniel.spring.springaifunctions.model.Question;
import com.lavalliere.daniel.spring.springaifunctions.model.WeatherResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.model.ModelOptionsUtils;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OpenAIServiceImpl implements OpenAIService {

    @Value("${sfg.aiapp.apiNinjasKey}")
    private String apiNinjasKey;

    private final OpenAiChatModel openAiChatModel;

    @Override
    public Answer getAnswer(Question question) {
        /*
        // Note on deprecation of FunctionCallback:  https://docs.spring.io/spring-ai/reference/api/tools-migration.html
        var promptOptions = OpenAiChatOptions.builder()
            .functionCallbacks(List.of(
                FunctionCallback.builder()
                    .function("CurrentWeather", new WeatherServiceFunction(apiNinjasKey))
                    .description("Get the current weather for a location")
                    .responseConverter(response -> {
                        String schema = ModelOptionsUtils.getJsonSchema(WeatherResponse.class, false);
                        String json = ModelOptionsUtils.toJsonString(response);
                        return schema + "\n" + json;
                    })
                    .build()))
            .build();
        */

        // Untested replacement version as I do not actually have a valid API_NINJAS_KEY
        var promptOptions = OpenAiChatOptions.builder().toolCallbacks(List.of(
            FunctionToolCallback.builder("currentWeather", new WeatherServiceFunction(apiNinjasKey))
            .description("Get the current weather for a location")
            .toolCallResultConverter((response, returnType)-> {
                String schema = ModelOptionsUtils.getJsonSchema(WeatherResponse.class, false);
                // Should actually use returnType to convert the response to the correct type,
                // but for simplicity we will just convert it to a JSON string
                String json = ModelOptionsUtils.toJsonString(response != null  ? response : "Null response was returned from the api");
                return schema + "\n" + json;
            }).build()
        )).build();

        Message userMessage = new PromptTemplate(question.question()).createMessage();
        Message systemMessage = new SystemPromptTemplate("You are a weather service. You receive weather information from a service which gives you the information based on the metrics system." +
            " When answering the weather in an imperial system country, you should convert the temperature to Fahrenheit and the wind speed to miles per hour. ").createMessage();

        var response = openAiChatModel.call(new Prompt(List.of(userMessage, systemMessage), promptOptions));
        return new Answer(response.getResult() != null ? response.getResult().getOutput().getText() : "Null response was returned");
    }
}