package com.lavalliere.daniel.spring.nullspecify.orders;

import lombok.Builder;
import org.jspecify.annotations.Nullable;

@Builder
public record Order(String email, @Nullable String promoCode) {
}
