package com.lavalliere.daniel.spring.springaiintro.services;

import com.lavalliere.daniel.spring.springaiintro.model.Answer;
import com.lavalliere.daniel.spring.springaiintro.model.GetCapitalRequest;
import com.lavalliere.daniel.spring.springaiintro.model.GetCapitalResponse;
import com.lavalliere.daniel.spring.springaiintro.model.Question;

public interface OpenAIService {
    String getAnswer(String question);
    Answer getAnswer(Question question);
    GetCapitalResponse getCapital(GetCapitalRequest getCapitalRequest);
    Answer getCapitalWithInfo(GetCapitalRequest getCapitalRequest);
}
