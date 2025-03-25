package com.lavalliere.daniel.spring.recipewebapp.repositories;

import com.lavalliere.daniel.spring.recipewebapp.domain.Category;
import com.lavalliere.daniel.spring.recipewebapp.domain.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {

    // An example of JPA Query Methods
    Optional<UnitOfMeasure> findByDescription(String description);
}
