package com.lavalliere.daniel.spring.springaiintro.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

// JsonPropertyDescription gives a hint as to what we want to bound it to
public record GetCapitalResponse(@JsonPropertyDescription("This is the city name") String answer) {
}
