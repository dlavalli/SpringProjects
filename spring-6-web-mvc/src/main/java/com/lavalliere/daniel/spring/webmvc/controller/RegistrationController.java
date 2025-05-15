package com.lavalliere.daniel.spring.webmvc.controller;

import com.lavalliere.daniel.spring.webmvc.model.Registration;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {

    @GetMapping("registration")
    public String getRegistration(@ModelAttribute("registration") Registration registration) {
        return "registration";
    }

    @PostMapping("registration")
    public String addRegistration(
        @Valid @ModelAttribute("registration") Registration registration,
        BindingResult result
    ) {
        if (result.hasErrors()) {
            System.err.println("There were errors");
            return "registration";
        }

        System.out.println("Registration: " + registration.getName());
        return "redirect:registration";
    }

}
