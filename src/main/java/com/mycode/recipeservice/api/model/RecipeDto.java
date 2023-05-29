package com.mycode.recipeservice.api.model;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.mycode.recipeservice.api.validation.ValidateDishType;
import com.mycode.recipeservice.api.validation.ValidateServing;
import com.mycode.recipeservice.domain.DishType;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RecipeDto {
    Long recipeId;
    @NotBlank(message = "RecipeName is mandatory")  
    String recipeName;
    @ValidateServing
    Long serving;
    @ValidateDishType(anyOf = {DishType.DESSERT, DishType.NON_VEGETARIAN, DishType.VEGETARIAN})
    DishType dishType;
    @NotBlank(message = "Instructions is Mandatory") 
    String instructions;
    @Valid List<IngredientDto> ingredients;
}
