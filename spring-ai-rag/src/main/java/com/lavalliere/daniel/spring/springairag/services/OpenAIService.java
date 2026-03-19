package com.lavalliere.daniel.spring.springairag.services;

import com.lavalliere.daniel.spring.springairag.model.Answer;
import com.lavalliere.daniel.spring.springairag.model.Question;

public interface OpenAIService {
    Answer getAnswer(Question question);
}
