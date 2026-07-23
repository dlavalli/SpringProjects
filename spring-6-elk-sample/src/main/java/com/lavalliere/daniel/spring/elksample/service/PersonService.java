package com.lavalliere.daniel.spring.elksample.service;

import com.lavalliere.daniel.spring.elksample.document.PersonDocument;
import com.lavalliere.daniel.spring.elksample.dto.PersonDTO;
import com.lavalliere.daniel.spring.elksample.repository.PersonRepository;
import com.lavalliere.daniel.spring.elksample.repository.helpers.SearchMetadata;
import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
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

    private SearchMetadata<PersonDocument> getSearchMetadata(@NotNull String name, @NotNull final Operator operator) {
        return SearchMetadata.<PersonDocument>builder()
            .field("name")
            .query(name)
            .operator(operator)
            .indexName("person")
            .documentClass(PersonDocument.class)
            .build();
    }

    public List<PersonDTO> findByName(@NotNull final String name) {
        return personRepository.searchWithMetadata(getSearchMetadata(name,Operator.And))
            .stream()
            .map(PersonDocument::toDTO)
            .collect(Collectors.toList());
    }

    public List<PersonDTO> findByNameTerms(@NotNull final String name) {
        return personRepository.searchWithMetadata(getSearchMetadata(name,Operator.Or))
            .stream()
            .map(PersonDocument::toDTO)
            .collect(Collectors.toList());
    }
}
