package com.lavalliere.daniel.springframework.spring5webapp.controllers;

import com.lavalliere.daniel.springframework.spring5webapp.repositories.AuthorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthorController {

    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @RequestMapping("/authors")
    public String getAuthors(Model model) {

        // Want to enhance the model with a list of authors
        model.addAttribute("authors", authorRepository.findAll());

        // Look for the list template inside the book directory
        return "authors/list";
    }
}


