package com.lavalliere.daniel.spring.recipewebapp.services;

import com.lavalliere.daniel.spring.recipewebapp.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {

    Set<UnitOfMeasureCommand> listAllUoms();
}