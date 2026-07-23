package com.lavalliere.daniel.spring.elksample.repository.helpers;

import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record SearchMetadata<T>(
    @NotNull String field,
    @NotNull String query,
    @NotNull Operator operator,
    @NotNull String indexName,
    @NotNull Class<T> documentClass
) {}