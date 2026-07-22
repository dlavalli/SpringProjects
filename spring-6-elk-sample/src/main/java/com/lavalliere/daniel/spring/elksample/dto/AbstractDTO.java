package com.lavalliere.daniel.spring.elksample.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public abstract class AbstractDTO {
    private String id;

    protected AbstractDTO(@NotBlank String id) {
        this.id = id;
    }
}
