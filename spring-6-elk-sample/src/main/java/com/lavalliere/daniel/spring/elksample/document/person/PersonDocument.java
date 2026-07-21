package com.lavalliere.daniel.spring.elksample.document.person;

import com.lavalliere.daniel.spring.elksample.dto.PersonDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;


@Builder
@Getter
@Setter
@Document(indexName="person")
public class PersonDocument {

    private final String id;
    private final String name;

    public PersonDocument(@NotBlank String id, @NotBlank String name) {
        this.id = id;
        this.name = name;
    }

    public PersonDTO toDTO() {
        return PersonDTO.builder().id(id).name(name).build();
    }
}
