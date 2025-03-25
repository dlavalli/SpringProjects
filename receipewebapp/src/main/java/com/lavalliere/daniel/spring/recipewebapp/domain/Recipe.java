package com.lavalliere.daniel.spring.recipewebapp.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Support autogeneration of id value
    private Long id;
    
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;

    @Lob
    private String directions;

    @OneToMany(cascade = CascadeType.ALL,
               mappedBy = "recipe",
               fetch = FetchType.EAGER)  // Mapped to a property call recipe in the corresponding relationship end
    private Set<Ingredient> ingredients = new HashSet<>();

    @Lob  // Allow for entity string member to be > 255 chars as a Binary large object (blob)
    private Byte[] image;

    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Notes notes;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "recipe_category",
               joinColumns = @JoinColumn(name = "recipe_id"),
               inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    public void setNotes(Notes notes) {
        this.notes = notes;
        if (notes != null) notes.setRecipe(this);
    }

    public Recipe addIngredient(Ingredient ingredient) {
        ingredient.setRecipe(this);
        this.ingredients.add(ingredient);
        return this;
    }
}
