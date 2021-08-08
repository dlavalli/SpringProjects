package com.lavalliere.daniel.spring.recipewebapp.repositories;

import com.lavalliere.daniel.spring.recipewebapp.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    // An example of JPA Query Methods
    Optional<Category> findByDescription(String description);
}
