package com.lavalliere.daniel.spring.recipewebapp.controllers;

import com.lavalliere.daniel.spring.recipewebapp.commands.RecipeCommand;
import com.lavalliere.daniel.spring.recipewebapp.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RecipeController {
    public final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));
        return "recipe/show";
    }

    @RequestMapping("recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipeform";
    }

    @RequestMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
        return  "recipe/recipeform";
    }

    @PostMapping()
    @RequestMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

        // So we're going to return back that and what we want to do here, we're going to
        // use a redirect and this is a command that tells Spring MVC to redirect to a
        // specific URL. We're going to append that with the ID of the saved object. So the
        // functionality is we'll see the form, it'll come up we'll enter in all the properties
        // that we want and then when we post it with the save, they'll go
        // into this. It will save to the database and then come back and we will redirect
        // to the recipe show and show the saved recipe
        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }
}
