package com.lavalliere.daniel.spring.recipewebapp.controllers;

import com.lavalliere.daniel.spring.recipewebapp.domain.Category;
import com.lavalliere.daniel.spring.recipewebapp.domain.Recipe;
import com.lavalliere.daniel.spring.recipewebapp.domain.UnitOfMeasure;
import com.lavalliere.daniel.spring.recipewebapp.repositories.CategoryRepository;
import com.lavalliere.daniel.spring.recipewebapp.repositories.UnitOfMeasureRepository;
import com.lavalliere.daniel.spring.recipewebapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Slf4j
@Controller
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
        log.debug("Getting index page");
        model.addAttribute("recipes", recipeService.getRecipes());
        return "index";
    }
}
