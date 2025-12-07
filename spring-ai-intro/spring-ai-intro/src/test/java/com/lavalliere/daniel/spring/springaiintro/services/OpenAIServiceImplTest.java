package com.lavalliere.daniel.spring.springaiintro.services;

import com.lavalliere.daniel.spring.springaiintro.model.Answer;
import com.lavalliere.daniel.spring.springaiintro.model.GetCapitalRequest;
import com.lavalliere.daniel.spring.springaiintro.model.GetCapitalResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static java.lang.IO.println;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OpenAIServiceImplTest {

    @Autowired
    OpenAIService openAIService;

    @Test
    void getAnswer() {
        String answer = openAIService.getAnswer("What is an AI's meaning of life?");
        println("=============================================================================");
        println(answer);
    }

    @Test
    void getCapital() {
        GetCapitalResponse response = openAIService.getCapital(new GetCapitalRequest("Canada"));
        println("=============================================================================");
        println(response.answer());
    }

    @Test
    void getCapitalWithInfo() {
        Answer answer = openAIService.getCapitalWithInfo(new GetCapitalRequest("Canada"));
        println("=============================================================================");
        println(answer);
    }
}