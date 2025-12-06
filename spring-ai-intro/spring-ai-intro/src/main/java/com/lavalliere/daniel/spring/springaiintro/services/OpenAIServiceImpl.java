package com.lavalliere.daniel.spring.springaiintro.services;

import com.lavalliere.daniel.spring.springaiintro.model.Answer;
import com.lavalliere.daniel.spring.springaiintro.model.GetCapitalRequest;
import com.lavalliere.daniel.spring.springaiintro.model.Question;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OpenAIServiceImpl implements OpenAIService {

    // Load the following file as a resource
    @Value("classpath:templates/get-capital-prompt.st")
    private Resource getCapitalPrompt;

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
    private Prompt getPrompt(GetCapitalRequest getCapitalRequest) {
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPrompt);
        return promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry()));
    }

    private String getAnswer(Prompt prompt) {
        ChatResponse response = chatModel.call(prompt);
        return response.getResult().getOutput().getText();
    }

    @Override
    public String getAnswer(String question) {
        Prompt prompt = getPrompt(question);
        ChatResponse response = chatModel.call(prompt);
        return response.getResult().getOutput().getText();
    }

    @Override
    public Answer getAnswer(Question question) {
        return new Answer(getAnswer(question.question()));
    }

    @Override
    public Answer getCapital(GetCapitalRequest getCapitalRequest) {
        return new Answer(getAnswer(getPrompt(getCapitalRequest)));
    }
}
