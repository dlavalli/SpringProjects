package com.lavalliere.daniel.spring.spring6restmvc.events;

import com.lavalliere.daniel.spring.spring6restmvc.domain.Beer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;

// DO NOT use @Data to avoid loop issues in beer and dependencies
@Builder
@Getter
@Setter
@AllArgsConstructor
public class BeerCreatedEvent {
    private Beer beer;
    private Authentication authentication;
}
