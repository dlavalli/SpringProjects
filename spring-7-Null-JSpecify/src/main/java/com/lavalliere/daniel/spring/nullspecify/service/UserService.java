package com.lavalliere.daniel.spring.nullspecify.service;

import com.lavalliere.daniel.spring.nullspecify.domain.User;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final List<User>  users = new ArrayList<>();
    
    public @Nullable User findUserByEmail(String email) {
        // @Nullable is used here to indicate possibly no match available
        // Could also return Optional<User>
        return users.stream().filter(u -> u.email().equalsIgnoreCase(email)).findFirst().orElse(null);
    }
}
