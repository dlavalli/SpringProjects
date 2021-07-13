package com.lavalliere.daniel.spring.petclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


// Because everythgin goes to owners, we can
@RequestMapping("/owners")
@Controller
public class OwnerController {
    @RequestMapping({"", "/index", "/index.html"})
    public String listOwners() {
        // Send back to thymeleaf the template filename (ie: index.html)
        return "owners/index";
    }
}
