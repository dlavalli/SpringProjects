package com.lavalliere.daniel.spring.elksample.repository;

import com.lavalliere.daniel.spring.elksample.document.PersonDocument;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface PersonCustomRepository {
    List<PersonDocument> findByName(@NotNull final String name);
    List<PersonDocument> findByNameTerms(@NotNull final String name);
}
