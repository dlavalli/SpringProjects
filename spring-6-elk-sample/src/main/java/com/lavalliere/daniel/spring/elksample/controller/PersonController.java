package com.lavalliere.daniel.spring.elksample.controller;

import com.lavalliere.daniel.spring.elksample.dto.PersonDTO;
import com.lavalliere.daniel.spring.elksample.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/{version}/person")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Returns 201 Created
    // POST http://localhost:8080/api/v1/person Body: { "id": "08ec1b05-d6da-4669-b02b-bbc4cc393ee6", "name": "Daniel"}  Type: Raw-JSON
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
    // GET http://localhost:8080/api/v1/person/search?name=Daniel%20Lavalliere
    @GetMapping(path = "/search")
    public ResponseEntity<List<PersonDTO>> searchByName(
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
    // GET http://localhost:8080/api/v1/person/searchByTerms?name=Daniel%20Lavalliere
    @GetMapping(path = "/searchByTerms")
    public ResponseEntity<List<PersonDTO>> searchByNameTerms(
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
    // GET http://localhost:8080/api/v1/person/08ec1b05-d6da-4669-b02b-bbc4cc393ff6
    @GetMapping(path = "/{id}")
    public ResponseEntity<PersonDTO> searchById(
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
