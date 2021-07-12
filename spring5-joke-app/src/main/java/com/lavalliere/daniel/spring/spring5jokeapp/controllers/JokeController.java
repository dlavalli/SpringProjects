package com.lavalliere.daniel.spring.spring5jokeapp.controllers;

import com.lavalliere.daniel.spring.spring5jokeapp.services.JokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class JokeController {
    private final JokeService jokeService;

    // Automatic since spring 4.6 with single constructor
    @Autowired
    public JokeController(JokeService jokeService) {
        this.jokeService = jokeService;
    }

    @RequestMapping({"/", ""})
    public String showJoke(Model model) {
        model.addAttribute("joke", jokeService.getJoke());

        // Tell Thymeleaf to look for a template file called index
        return "index";
    }
}
