package com.lavalliere.daniel.spring.springaiintro.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lavalliere.daniel.spring.springaiintro.model.Answer;
import com.lavalliere.daniel.spring.springaiintro.model.GetCapitalRequest;
import com.lavalliere.daniel.spring.springaiintro.model.GetCapitalResponse;
import com.lavalliere.daniel.spring.springaiintro.model.Question;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.management.MBeanAttributeInfo;
import java.util.Map;
import java.util.Objects;

import static java.lang.IO.println;

@Service
public class OpenAIServiceImpl implements OpenAIService {

    // Load the following file as a resource
    @Value("classpath:templates/get-capital-prompt.st")
    private Resource getCapitalPrompt;

    @Value("classpath:templates/get-capital-with-info.st")
    private Resource getCapitalPromptWithInfo;

    @Autowired
    private ObjectMapper mapper;

    private final ChatModel chatModel;  // Abstraction of implementations over the available LLMs
                                        // OpenAI in this case from pom.xml

    public OpenAIServiceImpl(ChatModel chatModel) {
        this.chatModel = chatModel;
    }


    private Prompt getPrompt(String question) {
        PromptTemplate promptTemplate = new PromptTemplate(question);
        return promptTemplate.create();
    }

    // An example using string templates
    private Prompt getPrompt(
        GetCapitalRequest getCapitalRequest,
        BeanOutputConverter<GetCapitalResponse> converter,
        Resource resource
    ) {
        String format = converter.getFormat();
        // println("format: \n"+ format);

        PromptTemplate promptTemplate = new PromptTemplate(resource);
        return promptTemplate.create(Map.of(
            "stateOrCountry", getCapitalRequest.stateOrCountry(),
            "format", format
            )
        );
    }

    private String getAnswer(Prompt prompt) {
        ChatResponse response = chatModel.call(prompt);
        return Objects.requireNonNull(response.getResult().getOutput().getText());
    }

    @Override
    public String getAnswer(String question) {
        Prompt prompt = getPrompt(question);
        ChatResponse response = chatModel.call(prompt);
        return Objects.requireNonNull(response.getResult().getOutput().getText());
    }

    @Override
    public Answer getAnswer(Question question) {
        return new Answer(getAnswer(question.question()));
    }

    @Override
    public GetCapitalResponse getCapital(GetCapitalRequest getCapitalRequest) {
        BeanOutputConverter<GetCapitalResponse> converter = new BeanOutputConverter<>(GetCapitalResponse.class);

        return converter.convert(getAnswer(getPrompt(getCapitalRequest, converter, getCapitalPrompt)));
    }

    @Override

    public Answer getCapitalWithInfo(GetCapitalRequest getCapitalRequest) {
        BeanOutputConverter<GetCapitalResponse> converter = new BeanOutputConverter<>(GetCapitalResponse.class);
        return new Answer(getAnswer(getPrompt(getCapitalRequest, converter, getCapitalPromptWithInfo)));
    }
}
