package com.mycode.recipeservice.repositoryJPA.mappers;

import org.springframework.lang.NonNull;

import com.mycode.recipeservice.common.mappers.Mapper;
import com.mycode.recipeservice.domain.Recipe;
import com.mycode.recipeservice.repositoryJPA.model.RecipeEntity;

public class ToRecipeEntityMapper implements Mapper<Recipe, RecipeEntity> {

    private final ToIngredientEntityMapper toIngredientEntityMapper = new ToIngredientEntityMapper();
    @Override
    @NonNull
    public RecipeEntity map(@NonNull Recipe source) {
        var entity = new RecipeEntity();
        entity.setRecipeId(source.getRecipeId());
        entity.setDishType(source.getDishType() != null ? source.getDishType().name() : null);
        entity.setRecipeName(source.getRecipeName());
        entity.setServing(source.getServing());
        entity.setInstructions(source.getInstructions());
        entity.setIngredient(source.getIngredients() != null ? toIngredientEntityMapper.map(source.getIngredients()) : null);
        return  entity;
    }
}
