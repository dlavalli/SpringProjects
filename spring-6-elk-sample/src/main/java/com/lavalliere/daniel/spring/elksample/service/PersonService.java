package com.lavalliere.daniel.spring.elksample.service;

import com.lavalliere.daniel.spring.elksample.document.PersonDocument;
import com.lavalliere.daniel.spring.elksample.dto.PersonDTO;
import com.lavalliere.daniel.spring.elksample.repository.PersonRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void create(@NotNull final PersonDTO personDto) {
        personRepository.save(personDto.toDocument());
    }

    public Optional<PersonDocument> findById(@NotNull final String id) {
        return personRepository.findById(id);
    }

    public List<PersonDocument> findByName(@NotNull final String name) {
        return personRepository.findByName(name);
    }

    public List<PersonDocument> findByNameTerms(@NotNull final String name) {
        return personRepository.findByNameTerms(name);
    }
}
