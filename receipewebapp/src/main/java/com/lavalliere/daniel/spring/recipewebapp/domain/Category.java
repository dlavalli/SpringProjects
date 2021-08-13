package com.lavalliere.daniel.spring.recipewebapp.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"recipes"})
@Entity
@Table(name = "CATEGORY")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Support autogeneration of id value
    private long id;

    private String description;

    @ManyToMany (mappedBy = "categories", fetch = FetchType.EAGER)
    private Set<Recipe> recipes;
}
