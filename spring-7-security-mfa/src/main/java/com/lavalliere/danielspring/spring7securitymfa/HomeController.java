package com.lavalliere.danielspring.spring7securitymfa;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public String home() {
        return "Hello World";
    }

    @GetMapping("/admin")
    public String admin() {
        return "Hello Admin";
    }
    @GetMapping("/ott/sent")
    public String tokenSent() {
        return "Token sent!";
    }

}
