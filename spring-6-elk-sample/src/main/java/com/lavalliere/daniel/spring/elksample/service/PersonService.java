package com.lavalliere.daniel.spring.elksample.service;

import com.lavalliere.daniel.spring.elksample.document.person.PersonDocument;
import com.lavalliere.daniel.spring.elksample.dto.PersonDTO;
import com.lavalliere.daniel.spring.elksample.repository.PersonRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public PersonDocument
    // Optional<PersonDocument>
    create(@NotNull final PersonDTO personDto) {
        //return Optional.of(
           return personRepository.save(personDto.toDocument());
        //);
    }
}
