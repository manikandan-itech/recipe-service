package com.mycode.recipeservice.repositoryJPA.model;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class IngredientEntityTestFactory {

    public static IngredientEntity ingredientEntity() {
        var entity = new IngredientEntity();
        entity.setIngredientName("IngredientName");
        entity.setQuantity(4);
        return entity;
    }
}
