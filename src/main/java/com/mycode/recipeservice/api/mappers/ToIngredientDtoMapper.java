package com.mycode.recipeservice.api.mappers;

import org.springframework.lang.NonNull;

import com.mycode.recipeservice.api.model.IngredientDto;
import com.mycode.recipeservice.common.mappers.Mapper;
import com.mycode.recipeservice.domain.Ingredient;

public class ToIngredientDtoMapper implements Mapper<Ingredient, IngredientDto> {

    @Override
    @NonNull
    public IngredientDto map(@NonNull Ingredient source) {
        return  IngredientDto.builder()//
                .ingredientName(source.getIngredientName())//
                .ingredientId(source.getIngredientId())//
                .quantity(source.getQuantity())//
                .build();
    }
}
