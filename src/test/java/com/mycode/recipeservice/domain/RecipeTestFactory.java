package com.mycode.recipeservice.domain;

import static lombok.AccessLevel.PRIVATE;

import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class RecipeTestFactory {

    public static Recipe recipe() {
        return Recipe.builder()
                .recipeId(1L)
                .dishType(DishType.VEGETARIAN)
                .instructions("Instructions")
                .recipeName("RecipeName")
                .serving(4L)
                .ingredients(List.of(getIngredient()))
                .build();
    }

    public static Recipe recipe(String dishType, String instructions) {
        return Recipe.builder()
                .recipeId(1L)
                .dishType(DishType.valueOf(dishType))
                .instructions(instructions)
                .recipeName("RecipeName")
                .serving(4L)
                .ingredients(List.of(getIngredient()))
                .build();
    }

    public static Recipe recipe(String recipeName, String instructions, Long serving, String dishType, List<Ingredient> ingredients) {
        return Recipe.builder()
                .dishType(dishType != null ? DishType.valueOf(dishType) : null)
                .instructions(instructions)
                .serving(serving)
                .recipeName(recipeName)
                .ingredients(ingredients)
                .build();
    }

    private static Ingredient getIngredient() {
        return Ingredient.builder().ingredientName("ingredient").build();
    }
}
