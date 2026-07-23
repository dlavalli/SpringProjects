package com.lavalliere.daniel.spring.elksample.repository;

import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;

import java.util.List;
import java.util.stream.Collectors;


public class PersonCustomRepositoryImpl<T> implements PersonCustomRepository<T> {

    // Use ElasticsearchOperations because it is the Spring abstraction that the repository layer
    // and most Spring Data features are built around instead of ElasticSearch
    // while ElasticsearchClient is the lower-level Elasticsearch Java API client underneath it
    private ElasticsearchOperations elasticsearchOperations;

    public PersonCustomRepositoryImpl(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    public List<T> searchWithMetadata(@NotNull SearchMetadata<T> searchMetadata) {

        // Use Operator.AND to ensure all space-separated tokens are matched
        // Using lambda to create MatchQuery
        MatchQuery matchQuery = MatchQuery.of(builder -> builder
            .field(searchMetadata.field())
            .query(searchMetadata.query())
            .operator(searchMetadata.operator())
        );

        // Using builder to create MatchQuery
        // MatchQuery matchQuery = new MatchQuery.Builder().field(searchMetadata.field()).query(searchMetadata.query()).operator(searchMetadata.operator()).build();

        return searchWithMatchQuery(matchQuery, searchMetadata);
    }

    private List<T> searchWithMatchQuery(
        @NotNull MatchQuery matchQuery,
        @NotNull SearchMetadata<T> searchMetadata
    ) {
        // Not mandatory to specify the index, but it's a good practice
        IndexCoordinates index = IndexCoordinates.of(searchMetadata.indexName());

        NativeQuery nativeQuery = NativeQuery.builder()
            .withQuery(matchQuery._toQuery())
            .build();

        SearchHits<T> searchHits = elasticsearchOperations.search(nativeQuery, searchMetadata.documentClass(), index);

        return searchHits.stream()
            .map(SearchHit::getContent)
            .collect(Collectors.toList());
    }
}
