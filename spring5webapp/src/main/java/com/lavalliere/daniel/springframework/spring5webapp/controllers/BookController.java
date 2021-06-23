package com.lavalliere.daniel.springframework.spring5webapp.controllers;

import com.lavalliere.daniel.springframework.spring5webapp.repositories.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

// Tell spring to turn this into a Spring MVC controller
@Controller
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @RequestMapping("/books")
    public String getBooks(Model model) {

        // Want to enhance the model with a list of books
        model.addAttribute("books", bookRepository.findAll());

        // Look for the list template inside the book directory
        return "books/list";
    }
}
