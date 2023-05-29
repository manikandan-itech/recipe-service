package com.mycode.recipeservice.api;

import com.mycode.recipeservice.api.model.RecipeDto;
import com.mycode.recipeservice.domain.DishType;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReciptDtoTestFactory {

    public static RecipeDto createRecipeDto(String recipeName, String instructions, Long serving, String dishType) {
        return RecipeDto.builder()
                .dishType(DishType.valueOf(dishType))
                .instructions(instructions)
                .serving(serving)
                .recipeName(recipeName)
                .build();
    }
}
