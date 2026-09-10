package com.lavalliere.daniel.spring7aisample3.controller;

import com.lavalliere.daniel.spring7aisample3.service.AIChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RagController {

    private final AIChatService aiChatService;

    @GetMapping("/askrag")
    public String rag(
        @RequestParam(name="question") String question
    ) {
        return aiChatService.queryVectorStoreString(question);
    }
}
