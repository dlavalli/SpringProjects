package com.lavalliere.daniel.spring.elksample.repository;

import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import com.lavalliere.daniel.spring.elksample.document.PersonDocument;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;

import java.util.List;
import java.util.stream.Collectors;

public class PersonCustomRepositoryImpl implements PersonCustomRepository {

    // Use ElasticsearchOperations because it is the Spring abstraction that the repository layer
    // and most Spring Data features are built around instead of ElasticSearch
    // while ElasticsearchClient is the lower-level Elasticsearch Java API client underneath it
    private final ElasticsearchOperations elasticsearchOperations;

    public PersonCustomRepositoryImpl(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Override
    public List<PersonDocument> findByName(String name) {
        // Use Operator.AND to ensure all space-separated tokens are matched
        // Using lambda to create MatchQuery
        MatchQuery matchQuery = MatchQuery.of(builder -> builder
            .field("name")
            .query(name)
            .operator(Operator.And)
        );

        // Using builder to create MatchQuery
        // MatchQuery matchQuery = new MatchQuery.Builder().field("name").query(name).operator(Operator.And).build();

        return searchByMatchQuery(matchQuery);
    }

    @Override
    public List<PersonDocument> findByNameTerms(@NotNull final String name) {
        // Use Operator.OR to match any of the space-separated tokens

        // Using lambda to create MatchQuery
        MatchQuery matchQuery = MatchQuery.of(builder -> builder
            .field("name")
            .query(name)
            .operator(Operator.Or)
        );

        // Using builder to create MatchQuery
        // MatchQuery matchQuery = new MatchQuery.Builder().field("name").query(name).operator(Operator.Or).build();

        return searchByMatchQuery(matchQuery);
    }

    private List<PersonDocument> searchByMatchQuery(MatchQuery matchQuery) {
        // Not mandatory to specify the index, but it's a good practice
        IndexCoordinates index = IndexCoordinates.of("person");

        NativeQuery nativeQuery = NativeQuery.builder()
            .withQuery(matchQuery._toQuery())
            .build();

        SearchHits<PersonDocument> searchHits = elasticsearchOperations.search(nativeQuery, PersonDocument.class, index);

        return searchHits.stream()
            .map(hit -> hit.getContent())
            .collect(Collectors.toList());
    }

}
