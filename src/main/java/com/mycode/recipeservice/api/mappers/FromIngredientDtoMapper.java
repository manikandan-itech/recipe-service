package com.mycode.recipeservice.api.mappers;

import org.springframework.lang.NonNull;

import com.mycode.recipeservice.api.model.IngredientDto;
import com.mycode.recipeservice.common.mappers.Mapper;
import com.mycode.recipeservice.domain.Ingredient;

public class FromIngredientDtoMapper implements Mapper<IngredientDto, Ingredient> {

    @Override
    @NonNull
    public Ingredient map(@NonNull IngredientDto source) {
        return  Ingredient.builder()
                .ingredientId(source.getIngredientId())
                .ingredientName(source.getIngredientName())
                .quantity(source.getQuantity())
                .build();
    }
}
