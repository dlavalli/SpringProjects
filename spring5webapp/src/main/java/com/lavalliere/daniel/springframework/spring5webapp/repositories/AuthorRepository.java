package com.lavalliere.daniel.springframework.spring5webapp.repositories;

import com.lavalliere.daniel.springframework.spring5webapp.domain.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {

}
