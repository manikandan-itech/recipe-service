package com.mycode.recipeservice.repositoryJPA.mappers;

import org.springframework.lang.NonNull;

import com.mycode.recipeservice.common.mappers.Mapper;
import com.mycode.recipeservice.domain.DishType;
import com.mycode.recipeservice.domain.Recipe;
import com.mycode.recipeservice.repositoryJPA.model.RecipeEntity;

public class FromRecipeEntityMapper implements Mapper<RecipeEntity, Recipe> {

    FromIngredientEntityMapper fromIngredientEntityMapper = new FromIngredientEntityMapper();

    @Override
    @NonNull
    public Recipe map(@NonNull RecipeEntity source) {
        return  Recipe.builder()
                .recipeId(source.getRecipeId())
                .dishType(DishType.valueOf(source.getDishType()))
                .recipeName(source.getRecipeName())
                .serving(source.getServing())
                .instructions(source.getInstructions())
                .ingredients(fromIngredientEntityMapper.map(source.getIngredient()))
                .build();
    }
}
