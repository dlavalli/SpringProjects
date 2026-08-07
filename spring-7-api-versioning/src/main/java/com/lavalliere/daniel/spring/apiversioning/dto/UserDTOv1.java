package com.lavalliere.daniel.spring.apiversioning.dto;

import lombok.Builder;

@Builder
public record UserDTOv1(Integer id, String name, String email, String website) {
}
