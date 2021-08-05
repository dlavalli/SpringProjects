package com.lavalliere.daniel.spring.petclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping({"", "/", "index", "index.html"})
    public String index() {
        // Send back to thymeleaf the template filename (ie: index.html)
        return "index";
    }
    @RequestMapping("/oups")
    public String oupsHandler() {
        return "notimplemented";
    }

}
