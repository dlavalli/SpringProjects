package com.lavalliere.daniel.spring.nullspecify.controller;

import com.lavalliere.daniel.spring.nullspecify.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/responses")
    public List<@Nullable String> getResponses() {
        List<@Nullable String> responses = reviewService.getResponses();
        responses.forEach(response -> log.info("response: {}", response));
        log.info("Responses: {}", responses);
        return responses;
    }

    @GetMapping("/responses/calculate-response-rate")
    public int calculateResponseRate() {
        return reviewService.calculateResponseRate();
    }
}
