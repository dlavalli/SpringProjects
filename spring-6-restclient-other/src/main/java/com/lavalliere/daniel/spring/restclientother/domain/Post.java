package com.lavalliere.daniel.spring.restclientother.domain;

import lombok.Builder;

@Builder
public record Post(Long id, Long userId, String title, String body) {
}
