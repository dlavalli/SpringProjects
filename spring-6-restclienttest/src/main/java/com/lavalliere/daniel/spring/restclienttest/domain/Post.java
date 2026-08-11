package com.lavalliere.daniel.spring.restclienttest.domain;

import lombok.Builder;

@Builder
public record Post(Long id, Long userId, String title, String body) {
}
