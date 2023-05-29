package com.mycode.recipeservice.repository;

import java.util.List;

import com.mycode.recipeservice.domain.Recipe;

public interface RecipeRespository {
    Recipe saveRecipe(Recipe recipe);
    List<Recipe> getRecipe(Recipe recipe);
    void updateRecipe(Recipe recipe);
    void deleteRecipe(Long recipeId);
}
