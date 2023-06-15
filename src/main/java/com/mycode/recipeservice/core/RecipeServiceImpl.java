package com.mycode.recipeservice.core;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mycode.recipeservice.domain.Recipe;
import com.mycode.recipeservice.repository.RecipeRespository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RecipeServiceImpl implements RecipeService{

    private final RecipeRespository recipeRespository;
    @Override
    public Recipe saveRecipe(Recipe recipe) {
        return recipeRespository.saveRecipe(recipe);
    }

    @Override
    public List<Recipe> searchRecipe(Recipe recipe) {
        return recipeRespository.searchRecipe(recipe);
    }

    @Override
    public void updateRecipe(Recipe recipe) {
        recipeRespository.updateRecipe(recipe);
    }

    @Override
    public void deleteRecipe(Long recipeId) {
        recipeRespository.deleteRecipe(recipeId);
    }
}
