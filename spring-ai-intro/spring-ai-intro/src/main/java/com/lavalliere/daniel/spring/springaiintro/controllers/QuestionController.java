package com.lavalliere.daniel.spring.springaiintro.controllers;

import com.lavalliere.daniel.spring.springaiintro.model.Answer;
import com.lavalliere.daniel.spring.springaiintro.model.GetCapitalRequest;
import com.lavalliere.daniel.spring.springaiintro.model.Question;
import com.lavalliere.daniel.spring.springaiintro.services.OpenAIService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionController {

    private final OpenAIService openAIService;

    public QuestionController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping("/ask")
    public Answer askQuestion(@RequestBody Question question) {
        return openAIService.getAnswer(question);
    }

    @PostMapping("/capital")
    public Answer getCapital(@RequestBody GetCapitalRequest getCapitalRequest)  {
        return openAIService.getCapital(getCapitalRequest);
    }
}
