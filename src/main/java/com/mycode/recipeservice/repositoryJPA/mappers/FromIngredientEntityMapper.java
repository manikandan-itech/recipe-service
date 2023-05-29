package com.mycode.recipeservice.repositoryJPA.mappers;

import org.springframework.lang.NonNull;

import com.mycode.recipeservice.common.mappers.Mapper;
import com.mycode.recipeservice.domain.Ingredient;
import com.mycode.recipeservice.repositoryJPA.model.IngredientEntity;

public class FromIngredientEntityMapper implements Mapper<IngredientEntity, Ingredient> {

    @Override
    @NonNull
    public Ingredient map(@NonNull IngredientEntity source) {
        return  Ingredient.builder()
                .ingredientId(source.getIngredientId())
                .ingredientName(source.getIngredientName())
                .quantity(source.getQuantity())
                .build();
    }
}
