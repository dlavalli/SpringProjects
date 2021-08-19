package com.lavalliere.daniel.spring.recipewebapp.controllers;

import com.lavalliere.daniel.spring.recipewebapp.domain.Category;
import com.lavalliere.daniel.spring.recipewebapp.domain.Recipe;
import com.lavalliere.daniel.spring.recipewebapp.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class IndexControllerTest {

    private IndexController indexController;

    @Mock
    private RecipeService recipeService;

    @Mock
    Model model;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        indexController = new IndexController(recipeService);
    }

    @Test
    public void testMockMVC() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void getIndexPage() {
        // given
        Set<Recipe> recipes = new HashSet<>();

        // Strange issue here where the second new Recipe is not recognized with new as argument
        // ie: it assumed both objects were the same instead of differetn ones
        recipes.add(new Recipe());
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipes.add(recipe);

        // when
        when(recipeService.getRecipes()).thenReturn(recipes);

        String viewName = indexController.getIndexPage(model);

        // To capture what was passed as argument to the mock
        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        assertEquals("index",viewName);
        verify(recipeService, times(1)).getRecipes();

        // Make sure we get back a set with 2 elements in it
        verify(model, times(1)).addAttribute(   eq("recipes"), argumentCaptor.capture());

        Set<Recipe> setInController = argumentCaptor.getValue();
        assertEquals(2, setInController.size());
    }
}