package com.lavalliere.daniel.spring.recipewebapp.commands;

import com.lavalliere.daniel.spring.recipewebapp.domain.Category;
import com.lavalliere.daniel.spring.recipewebapp.domain.Difficulty;
import com.lavalliere.daniel.spring.recipewebapp.domain.Ingredient;
import com.lavalliere.daniel.spring.recipewebapp.domain.Notes;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {
    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Set<IngredientCommand> ingredients = new HashSet<>();
    private Difficulty difficulty;
    private NotesCommand notes;
    private Set<CategoryCommand> categories = new HashSet<>();
}
