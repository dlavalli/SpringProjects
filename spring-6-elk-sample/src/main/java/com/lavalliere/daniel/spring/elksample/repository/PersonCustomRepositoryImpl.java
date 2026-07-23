package com.lavalliere.daniel.spring.elksample.repository;

import com.lavalliere.daniel.spring.elksample.repository.helpers.ELKSearchHelper;
import com.lavalliere.daniel.spring.elksample.repository.helpers.SearchMetadata;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import java.util.List;


public class PersonCustomRepositoryImpl<T> implements PersonCustomRepository<T> {

    // Use ElasticsearchOperations because it is the Spring abstraction that the repository layer
    // and most Spring Data features are built around instead of ElasticSearch
    // while ElasticsearchClient is the lower-level Elasticsearch Java API client underneath it
    private final ELKSearchHelper<T> searchHelper;

    public PersonCustomRepositoryImpl(ElasticsearchOperations elasticsearchOperations) {
        this.searchHelper = new ELKSearchHelper<>(elasticsearchOperations);
    }

    @Override
    public List<T> searchWithMetadata(@NotNull SearchMetadata<T> searchMetadata) {
        return searchHelper.searchWithMetadata(searchMetadata);
    }
}
