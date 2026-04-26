package com.lavalliere.daniel.spring.springaifunctions.services;

import com.lavalliere.daniel.spring.springaifunctions.model.Answer;
import com.lavalliere.daniel.spring.springaifunctions.model.Question;

public interface OpenAIService {
    Answer getAnswer(Question question);
}

