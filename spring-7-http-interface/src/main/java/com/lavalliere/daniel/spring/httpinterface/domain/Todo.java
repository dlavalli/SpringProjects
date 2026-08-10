package com.lavalliere.daniel.spring.httpinterface.domain;

import lombok.Builder;


// Matching based on services from:  http://jsonplaceholder.typicode.com/
@Builder
public record Todo(Long id, Long userId, String title, Boolean completed) {
}
