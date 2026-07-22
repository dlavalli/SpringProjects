package com.lavalliere.daniel.spring.elksample.controller;

import com.lavalliere.daniel.spring.elksample.dto.PersonDTO;
import com.lavalliere.daniel.spring.elksample.service.IndexService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/{version}/index")
public class IndexController {

    private final IndexService indexService;

    public IndexController(IndexService indexService) {
        this.indexService = indexService;
    }

    // POST http://localhost:8080/api/v1/index
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Returns 201 Created
    private void create(
        @PathVariable("version") String version
    ) {
        switch (version.toLowerCase()) {
            case "v1":
            default:
               indexService.createIndices();
        }
    }
}
