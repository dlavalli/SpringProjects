package com.lavalliere.danielspring.spring7reactiverest.todo;

import lombok.Builder;

@Builder
public record Todo(Long id, Long userId, String title, Boolean completed) {
}
