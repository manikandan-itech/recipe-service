package com.mycode.recipeservice.core;

import java.util.List;

import com.mycode.recipeservice.domain.Recipe;

public interface RecipeService {
    Recipe saveRecipe(Recipe recipe);
    List<Recipe> getRecipe(Recipe recipe);
    void updateRecipe(Recipe recipe);
    void deleteRecipe(Long recipeId);
}
