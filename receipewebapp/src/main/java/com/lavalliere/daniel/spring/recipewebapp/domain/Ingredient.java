package com.lavalliere.daniel.spring.recipewebapp.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"recipe"})
@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Support autogeneration of id value
    private Long id;

    private String description;
    private BigDecimal amount;

    @OneToOne (fetch = FetchType.EAGER) // unidirectional, No cascade, do not want to delete recipe when deleting ingredients
    private UnitOfMeasure uom;

    @ManyToOne(fetch = FetchType.EAGER)  // unidirectional, No cascade, do not want to delete recipe when deleting ingredients
    private Recipe recipe;

    public Ingredient() {
    }

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
    }

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom, Recipe recipe) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
        this.recipe = recipe;
    }
}
