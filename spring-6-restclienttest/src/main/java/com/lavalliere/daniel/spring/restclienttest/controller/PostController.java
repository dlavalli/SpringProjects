package com.lavalliere.daniel.spring.restclienttest.controller;

import com.lavalliere.daniel.spring.restclienttest.domain.Post;
// import com.lavalliere.daniel.spring.restclientother.service.PostService;
import com.lavalliere.daniel.spring.restclienttest.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService service;

    @GetMapping()
    public List<Post> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Post findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@RequestBody Post post) {
        return service.create(post);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@PathVariable Long id, @RequestBody Post post) {
        return service.update(id, post);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
