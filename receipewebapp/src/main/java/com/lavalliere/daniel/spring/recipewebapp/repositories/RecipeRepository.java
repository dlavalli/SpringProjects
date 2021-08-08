package com.lavalliere.daniel.spring.recipewebapp.repositories;

import com.lavalliere.daniel.spring.recipewebapp.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
