package com.lavalliere.daniel.spring.restclientinterceptor.domain;

import lombok.Builder;

@Builder
public record Post(Long id, Long userId, String title, String body) {
}
