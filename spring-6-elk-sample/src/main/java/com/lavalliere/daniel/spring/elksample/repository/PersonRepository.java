package com.lavalliere.daniel.spring.elksample.repository;

import com.lavalliere.daniel.spring.elksample.document.PersonDocument;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Optional;



public interface PersonRepository extends ElasticsearchRepository<PersonDocument,String>, PersonCustomRepository { }
