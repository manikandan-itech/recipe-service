package com.mycode.recipeservice.domain;

import java.util.List;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@Builder
@With
public class Recipe {
    private Long recipeId;
    private String recipeName;
    private Long serving;
    private DishType dishType;
    private String instructions;
    private List<Ingredient> ingredient;
}
