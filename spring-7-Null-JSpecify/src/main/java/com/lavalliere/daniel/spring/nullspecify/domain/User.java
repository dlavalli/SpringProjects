package com.lavalliere.daniel.spring.nullspecify.domain;

import lombok.Builder;

@Builder
public record User(Integer id, String firstName, String lastName, String email) {
}
