package com.lavalliere.daniel.spring.recipewebapp.commands;

import com.lavalliere.daniel.spring.recipewebapp.domain.Recipe;
import com.lavalliere.daniel.spring.recipewebapp.domain.UnitOfMeasure;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {
    private Long id;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasureCommand uom;
}
