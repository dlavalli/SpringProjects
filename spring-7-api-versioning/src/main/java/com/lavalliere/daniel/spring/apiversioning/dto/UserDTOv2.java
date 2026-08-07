package com.lavalliere.daniel.spring.apiversioning.dto;

import lombok.Builder;

@Builder
public record UserDTOv2(Integer id, String firstName, String lastName, String email) {
}
