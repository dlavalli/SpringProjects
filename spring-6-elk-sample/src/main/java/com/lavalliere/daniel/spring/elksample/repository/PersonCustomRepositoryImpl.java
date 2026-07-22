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

import java.util.List;
import java.util.stream.Collectors;

public class PersonCustomRepositoryImpl implements PersonCustomRepository {

    private final ElasticsearchOperations elasticsearchOperations;

    public PersonCustomRepositoryImpl(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Override
    public List<PersonDocument> findByName(String name) {
        // Use Operator.AND to ensure all space-separated tokens are matched
        MatchQuery matchQuery = MatchQuery.of(m -> m
            .field("name")
            .query(name)
            .operator(Operator.And)
        );
        return searchByMatchQuery(matchQuery);
    }

    @Override
    public List<PersonDocument> findByNameTerms(@NotNull final String name) {
        // Use Operator.OR to match any of the space-separated tokens
        MatchQuery matchQuery = MatchQuery.of(m -> m
            .field("name")
            .query(name)
            .operator(Operator.Or)
        );
        return searchByMatchQuery(matchQuery);
    }

    private List<PersonDocument> searchByMatchQuery(MatchQuery matchQuery) {
        NativeQuery nativeQuery = NativeQuery.builder()
            .withQuery(matchQuery._toQuery())
            .build();

        SearchHits<PersonDocument> searchHits = elasticsearchOperations.search(nativeQuery, PersonDocument.class);

        return searchHits.stream()
            .map(hit -> hit.getContent())
            .collect(Collectors.toList());
    }

}
