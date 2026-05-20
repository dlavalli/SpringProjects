package com.lavalliere.daniel.spring.kafkaorderdispatch.message;

import lombok.Builder;

import java.util.UUID;

@Builder
public record OrderCreated(UUID orderId, String item ) {
}
