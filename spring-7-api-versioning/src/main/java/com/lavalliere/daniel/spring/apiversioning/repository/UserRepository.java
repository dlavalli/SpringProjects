package com.lavalliere.daniel.spring.apiversioning.repository;

import com.lavalliere.daniel.spring.apiversioning.domain.User;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    private final List<User> users = new ArrayList<>();

    public List<User> findAll() {
        return users;
    }

    public User findById(Integer id) {
        return users.stream().filter(u -> u.id().equals(id)).findFirst().orElse(null);
    }

    @PostConstruct
    public void init() {
        users.add(User.builder().id(1).name("Daniel Lavalliere").email("daniel.mail.com").build());
    }
}
