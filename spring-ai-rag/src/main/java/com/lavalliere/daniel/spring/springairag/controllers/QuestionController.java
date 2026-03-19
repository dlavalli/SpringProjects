package com.lavalliere.daniel.spring.springairag.controllers;

import com.lavalliere.daniel.spring.springairag.model.Answer;
import com.lavalliere.daniel.spring.springairag.model.Question;
import com.lavalliere.daniel.spring.springairag.services.OpenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class QuestionController {

    private final OpenAIService openAIService;

    @PostMapping("/ask")
    public Answer ask(@RequestBody Question question) {
        return openAIService.getAnswer(question);
    }
}
