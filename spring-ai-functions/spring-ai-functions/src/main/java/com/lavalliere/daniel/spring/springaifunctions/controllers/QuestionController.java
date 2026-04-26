package com.lavalliere.daniel.spring.springaifunctions.controllers;

import com.lavalliere.daniel.spring.springaifunctions.model.Answer;
import com.lavalliere.daniel.spring.springaifunctions.model.Question;
import com.lavalliere.daniel.spring.springaifunctions.services.OpenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class QuestionController {

    private final OpenAIService openAIService;

    @PostMapping("/weather")
    public Answer askQuestion(@RequestBody Question question) {
        return openAIService.getAnswer(question);
    }

}