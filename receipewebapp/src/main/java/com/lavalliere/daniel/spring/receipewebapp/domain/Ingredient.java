package com.lavalliere.daniel.spring.receipewebapp.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Support autogeneration of id value
    private long id;

    private String description;
    private BigDecimal amount;

    // private UnitOfMeasure uom;

    @ManyToOne  // No cascade, do not want to delete recipe when deleting ingredients
    private Recipe receipe;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Recipe getReceipe() {
        return receipe;
    }

    public void setReceipe(Recipe receipe) {
        this.receipe = receipe;
    }
}
