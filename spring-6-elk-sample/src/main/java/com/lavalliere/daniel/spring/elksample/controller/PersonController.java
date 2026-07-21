package com.lavalliere.daniel.spring.elksample.controller;

import com.lavalliere.daniel.spring.elksample.document.person.PersonDocument;
import com.lavalliere.daniel.spring.elksample.dto.PersonDTO;
import com.lavalliere.daniel.spring.elksample.service.PersonService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/{version}/person")
/*
  Since testing on a single node local ELK environment, i had to run the following so that the /person index have a green status
  else the status was yellow because no shard was allocated for it on at least 1 replica
  PUT /person/_settings
  {
    "index" : {
      "number_of_replicas" : 0
    }
  }
 */
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    private ResponseEntity<PersonDocument> create(
        @PathVariable("version") String version,  // Only required if {version} is not hardcoded to a specific version ex: v1
        @RequestBody final PersonDTO personDTO
    ) {
         switch (version.toLowerCase()) {
            case "v1":
            default:
                return ResponseEntity.ofNullable(personService.create(personDTO));
        }
    }

    /*
    @GetMapping
    public ResponseEntity<?> getPerson(@PathVariable("version") String version) {
        switch (version.toLowerCase()) {
            case "v1":
                return ResponseEntity.ok(executeV1Logic());
            case "v2":
                return ResponseEntity.ok(executeV2Logic());
            default:
                return ResponseEntity.badRequest().body("Unsupported API version");
        }
    }
     */
}
