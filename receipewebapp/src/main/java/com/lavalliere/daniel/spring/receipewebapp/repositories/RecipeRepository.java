package com.lavalliere.daniel.spring.receipewebapp.repositories;

import com.lavalliere.daniel.spring.receipewebapp.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
