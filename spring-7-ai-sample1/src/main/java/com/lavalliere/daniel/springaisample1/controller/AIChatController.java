package com.lavalliere.daniel.springaisample1.controller;

import com.lavalliere.daniel.springaisample1.service.AIChatService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/chat")
public class AIChatController {

    private final AIChatService chatService;

    @GetMapping
    public ResponseEntity<String> sendPrompt(@RequestParam(name="prompt", required = true) String prompt) {
        return ResponseEntity.ok(chatService.sendPrompt(prompt));
    }
}
