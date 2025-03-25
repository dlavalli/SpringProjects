package guru.springframework.spring6webapps.controllers;

import guru.springframework.spring6webapps.services.AuthorService;
import guru.springframework.spring6webapps.services.PublisherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PublisherController {
    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @RequestMapping("/publishers")
    public String getPublishers(Model model) {
        model.addAttribute("publishers", publisherService.findAll());
        return "publishers";  // Return the view name
    }
}
