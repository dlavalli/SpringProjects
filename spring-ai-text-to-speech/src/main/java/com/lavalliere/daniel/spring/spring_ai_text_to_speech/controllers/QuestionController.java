package com.lavalliere.daniel.spring.spring_ai_text_to_speech.controllers;

import com.lavalliere.daniel.spring.spring_ai_text_to_speech.model.Question;
import com.lavalliere.daniel.spring.spring_ai_text_to_speech.services.OpenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class QuestionController {

    private final OpenAIService openAIService;

    @PostMapping(value ="/talk", produces = "audio/mpeg")
    public byte[] talkTalk(@RequestBody Question question) {
        return openAIService.getSpeech(question);
    }
}