package com.lavalliere.daniel.spring.elksample.repository;

import com.lavalliere.daniel.spring.elksample.repository.helpers.SearchMetadata;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface PersonCustomRepository<T> {
    List<T> searchWithMetadata(@NotNull SearchMetadata<T> searchMetadata);
}
