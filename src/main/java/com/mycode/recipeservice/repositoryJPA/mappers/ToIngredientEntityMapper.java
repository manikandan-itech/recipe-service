package com.mycode.recipeservice.repositoryJPA.mappers;

import org.springframework.lang.NonNull;

import com.mycode.recipeservice.common.mappers.Mapper;
import com.mycode.recipeservice.domain.Ingredient;
import com.mycode.recipeservice.repositoryJPA.model.IngredientEntity;

public class ToIngredientEntityMapper implements Mapper<Ingredient, IngredientEntity> {

    @Override
    @NonNull
    public IngredientEntity map(@NonNull Ingredient source) {
        var entity = new IngredientEntity();
        entity.setIngredientId(source.getIngredientId());
        entity.setIngredientName(source.getIngredientName());
        entity.setQuantity(source.getQuantity());
        return  entity;
    }
}
