package com.lavalliere.daniel.spring.elksample.controller;

import com.lavalliere.daniel.spring.elksample.document.PersonDocument;
import com.lavalliere.daniel.spring.elksample.dto.PersonDTO;
import com.lavalliere.daniel.spring.elksample.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @ResponseStatus(HttpStatus.CREATED) // Returns 201 Created
    private void create(
        @PathVariable("version") String version,  // Only required if {version} is not hardcoded to a specific version ex: v1
        @RequestBody final PersonDTO personDTO
    ) {
         switch (version.toLowerCase()) {
            case "v1":
            default:
                personService.create(personDTO);
        }
    }


    // ie: /api/{version}/person/searchByName?name=xxxx
    @GetMapping(path = "/search")
    public ResponseEntity<List<PersonDocument>> searchByName(
        @PathVariable("version") String version,
        @RequestParam(value="name") String name  // required=true by default
    ) {
        switch (version.toLowerCase()) {
            case "v1":
            default:
                return ResponseEntity.ok(personService.findByName(name));
        }
    }

    // ie: /api/{version}/person/searchByTerms?name=xxxx
    @GetMapping(path = "/searchByTerms")
    public ResponseEntity<List<PersonDocument>> searchByNameTerms(
        @PathVariable("version") String version,
        @RequestParam(value="name") String name  // required=true by default
    ) {
        switch (version.toLowerCase()) {
            case "v1":
            default:
                return ResponseEntity.ok(personService.findByNameTerms(name));
        }
    }

    // ie: /api/{version}/person/{id}
    @GetMapping(path = "/{id}")
    public ResponseEntity<PersonDocument> searchById(
        @PathVariable("version") String version,
        @PathVariable("id")  String id
    ) {
        switch (version.toLowerCase()) {
            case "v1":
            default:
                return ResponseEntity.of(personService.findById(id));
        }
    }

}
