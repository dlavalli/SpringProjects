package com.lavalliere.daniel.spring.elksample.repository;

import com.lavalliere.daniel.spring.elksample.document.PersonDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PersonRepository extends ElasticsearchRepository<PersonDocument,String>, PersonCustomRepository<PersonDocument> { }
