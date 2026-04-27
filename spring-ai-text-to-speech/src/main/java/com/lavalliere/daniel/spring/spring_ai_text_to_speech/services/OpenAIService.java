package com.lavalliere.daniel.spring.spring_ai_text_to_speech.services;

import com.lavalliere.daniel.spring.spring_ai_text_to_speech.model.Question;

public interface OpenAIService {
    byte[] getSpeech(Question question);
}
