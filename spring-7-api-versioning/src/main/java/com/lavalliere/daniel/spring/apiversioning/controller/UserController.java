package com.lavalliere.daniel.spring.apiversioning.controller;

import com.lavalliere.daniel.spring.apiversioning.domain.User;
import com.lavalliere.daniel.spring.apiversioning.dto.UserDTOv1;
import com.lavalliere.daniel.spring.apiversioning.dto.UserDTOv2;
import com.lavalliere.daniel.spring.apiversioning.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
// @RequestMapping("/api/v1/users")  -> Normal way of doing this
@RequestMapping("/api")   // Remaining part of the path moves with version to the methods
public class UserController {

    private final UserRepository userRepository;

    // @GetMapping     -> Normal way of doing this
    @GetMapping(value="/{version}/users", version="1.0")   // Call with: GET http://localhost:8080/api/1.0/users
    public List<UserDTOv1> findAllv1(@PathVariable String version) {
        return userRepository.findAll().stream().map(User::toDTOv1).toList();
    }

    // To reach this version would need header "X-API-Version" set to "1.1"
    @GetMapping(value="/users", version="1.1")   // Call with: GET http://localhost:8080/api/1.0/users
    public List<UserDTOv1> findAllv1_1(@PathVariable String version) {
        return userRepository.findAll().stream().map(User::toDTOv1).toList();
    }

    @GetMapping(value="/{version}/users", version="2.0")   // Call with: GET http://localhost:8080/api/2.0/users
    public List<UserDTOv2> findAllv2(@PathVariable String version) {
        return userRepository.findAll().stream().map(User::toDTOv2).toList();
    }
}
