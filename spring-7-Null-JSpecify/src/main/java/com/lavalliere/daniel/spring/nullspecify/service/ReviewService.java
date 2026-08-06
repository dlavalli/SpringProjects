package com.lavalliere.daniel.spring.nullspecify.service;

import com.lavalliere.daniel.spring.nullspecify.domain.User;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ReviewService {

private final List<@Nullable String> responses = new ArrayList<>();


    // Collections with Nullable elements but the collection is non-null
    // ie: some questions could be skipped by the customers
    public List<@Nullable String> getResponses() {
        responses.add("Excellent service");
        responses.add(null);
        responses.add("Coffee was too hot");
        responses.add(null);
        responses.add("Would visit again");
        return responses;
    }

    public int calculateResponseRate() {
        long answered = responses.stream().filter(Objects::nonNull).count();
        return (int) (answered * 100 / responses.size());
    }
}
