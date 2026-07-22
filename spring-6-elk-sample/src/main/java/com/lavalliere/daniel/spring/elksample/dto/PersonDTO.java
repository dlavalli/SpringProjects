package com.lavalliere.daniel.spring.elksample.dto;

import com.lavalliere.daniel.spring.elksample.document.PersonDocument;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class PersonDTO extends AbstractDTO {

    private String name;

    public PersonDTO(@NotBlank String id, @NotBlank String name) {
        super(id);
        this.name = name;
    }
    public PersonDocument toDocument() {
        return PersonDocument.builder().id(getId()).name(getName()).build();
    }
}
