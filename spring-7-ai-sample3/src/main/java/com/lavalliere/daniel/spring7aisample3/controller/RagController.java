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
    public String askRag(
        @RequestParam(name="question", required = true) String question

    ) {
        // Would normally have a way to generate unique ID for each user connecting
        // so that we can use it to associate it with a persistence. For security
        // That would be internally assigned for security reason.
        // For testing purposes, provide info from api directly

        // Updating ChatMemory manually directly from the code
        // return aiChatService.queryVectorStoreString(question, "anonymous");

        // Updating ChatMemory using an advisor instead (better)
        return aiChatService.queryVectorStoreAdvised(question, "anonymous");
    }
}
