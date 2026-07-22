package com.lavalliere.daniel.spring.elksample.document;

import com.lavalliere.daniel.spring.elksample.dto.PersonDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

@Getter
@Setter
@SuperBuilder
@Document(indexName="person")  // On its own (ie: without mapping annotation) This creates an index with default mappings, default settings
@Mapping(mappingPath = "static/personMapping.json")
@Setting(settingPath = "static/personSetting.json")
@NoArgsConstructor
public class PersonDocument extends AbstractDocument {

    private String name;

    public PersonDocument(@NotBlank String id, @NotBlank String name) {
        super(id);
        this.name = name;
    }

    public PersonDTO toDTO() {
        return PersonDTO.builder().id(getId()).name(getName()).build();
    }
}
