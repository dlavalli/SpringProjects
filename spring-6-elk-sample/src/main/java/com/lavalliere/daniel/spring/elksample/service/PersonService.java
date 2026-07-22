package com.lavalliere.daniel.spring.elksample.service;

import com.lavalliere.daniel.spring.elksample.document.PersonDocument;
import com.lavalliere.daniel.spring.elksample.dto.PersonDTO;
import com.lavalliere.daniel.spring.elksample.repository.PersonRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void create(@NotNull final PersonDTO personDto) {
        personRepository.save(personDto.toDocument());
    }

    public Optional<PersonDTO> findById(@NotNull final String id) {
        return personRepository.findById(id).map(PersonDocument::toDTO);
    }

    public List<PersonDTO> findByName(@NotNull final String name) {
        return personRepository.findByName(name).stream()
                .map(PersonDocument::toDTO)
                .collect(Collectors.toList());
    }

    public List<PersonDTO> findByNameTerms(@NotNull final String name) {
        return personRepository.findByNameTerms(name).stream()
            .map(PersonDocument::toDTO)
            .collect(Collectors.toList());
    }
}
