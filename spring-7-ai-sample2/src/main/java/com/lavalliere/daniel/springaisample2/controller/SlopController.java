package com.lavalliere.daniel.springaisample2.controller;

import com.lavalliere.daniel.springaisample2.service.AIChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SlopController {

    private final AIChatService aiChatService;

    // The intent is to take a popular tweet and alter it to make it ouw own (ie: slop generation)

    @GetMapping("/tweets")
    public AIChatService.NewTweets tweets(
        @RequestParam(defaultValue="How to learn to program fast!") String originalTweet,
        @RequestParam(defaultValue="Spring AI") String topicHint,
        @RequestParam(defaultValue="high") String emojiLevel,
        @RequestParam(defaultValue="extreme") String modernizationLevel
    ){
        // return ResponseEntity.ok(chatService.sendSimpsonsTrivia(prompt));
        return aiChatService.sendPrompt(
            originalTweet,
            topicHint,
            emojiLevel,
            modernizationLevel);
    }
}
