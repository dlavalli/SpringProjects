package com.lavalliere.daniel.spring.recipewebapp.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "unit_of_measure")
public class UnitOfMeasure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Support autogeneration of id value
    private long id;
    private String description;
}
