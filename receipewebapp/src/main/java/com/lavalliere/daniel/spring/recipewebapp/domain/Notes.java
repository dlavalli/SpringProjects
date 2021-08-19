package com.lavalliere.daniel.spring.recipewebapp.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(exclude = {"recipe"})
@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Support autogeneration of id value
    private Long id;

    @OneToOne (fetch = FetchType.EAGER) // Do not want to delete Recipe if delete notes
    private Recipe recipe;

    @Lob   // Allow for entity string member to be > 255 chars as a Binary large object (blob)
    private String recipeNotes;
}
