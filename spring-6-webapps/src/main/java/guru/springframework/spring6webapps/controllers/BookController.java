package guru.springframework.spring6webapps.controllers;

// import org.springframework.web.bind.annotation.RestController;

import guru.springframework.spring6webapps.services.BookService;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

// @RestController
@Controller
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping("/books")
    public String getBooks(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "books";  // Return the view name
    }
}
