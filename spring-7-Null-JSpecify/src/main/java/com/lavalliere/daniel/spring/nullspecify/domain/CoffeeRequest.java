package com.lavalliere.daniel.spring.nullspecify.domain;

import org.jspecify.annotations.Nullable;

public record CoffeeRequest(String email, String coffeeType, String size, @Nullable String milk, @Nullable String syrup) {
    public CoffeeRequest
    {
        if (email.isEmpty()) throw new IllegalArgumentException("email is required");
        if (coffeeType.isEmpty()) throw new IllegalArgumentException("coffeeType is required");
    }
}
