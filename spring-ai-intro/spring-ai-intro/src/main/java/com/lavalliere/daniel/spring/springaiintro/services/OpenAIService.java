package com.lavalliere.daniel.spring.springaiintro.services;

import com.lavalliere.daniel.spring.springaiintro.model.Answer;
import com.lavalliere.daniel.spring.springaiintro.model.GetCapitalRequest;
import com.lavalliere.daniel.spring.springaiintro.model.GetCapitalResponse;
import com.lavalliere.daniel.spring.springaiintro.model.Question;

public interface OpenAIService {
    // See also project on prompt engineering  :  https://github.com/springframeworkguru/spring-ai-prompt-engineering
    String getAnswer(String question);
    Answer getAnswer(Question question);
    GetCapitalResponse getCapital(GetCapitalRequest getCapitalRequest);
    Answer getCapitalWithInfo(GetCapitalRequest getCapitalRequest);
}
