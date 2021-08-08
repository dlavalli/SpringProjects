package com.lavalliere.daniel.spring.recipewebapp.services;

import com.lavalliere.daniel.spring.recipewebapp.domain.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();
}
