package com.lavalliere.daniel.spring.webmvc.controller;

import com.lavalliere.daniel.spring.webmvc.model.Registration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {

    @PostMapping("/registration")
    public String addRegistration(@ModelAttribute("registration") Registration registration) {
        // System.out.println("Registration: " + registration.getName());
        return "registration";
    }

    @GetMapping("/registration")
    public String registration(@ModelAttribute("registration") Registration registration) {
        return "registration";
    }
}
