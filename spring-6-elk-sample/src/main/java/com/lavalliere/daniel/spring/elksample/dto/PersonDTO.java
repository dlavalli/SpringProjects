package com.lavalliere.daniel.spring.elksample.dto;

import com.lavalliere.daniel.spring.elksample.document.person.PersonDocument;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {
    @NotBlank(message = "Id cannot be blank or null") private String id;
    @NotBlank(message = "Name cannot be blank or null") private String name;

    public PersonDocument toDocument() {
        return PersonDocument.builder().id(id).name(name).build();
    }
}
