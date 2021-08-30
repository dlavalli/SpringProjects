package com.lavalliere.daniel.spring.recipewebapp.commands;

import com.lavalliere.daniel.spring.recipewebapp.domain.Recipe;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotesCommand {
    private Long id;
    private String recipeNotes;
}
