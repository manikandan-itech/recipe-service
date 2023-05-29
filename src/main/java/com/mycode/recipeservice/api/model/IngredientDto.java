package com.mycode.recipeservice.api.model;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class IngredientDto {
    Long ingredientId;
    @NotBlank(message = "IngredientName is Mandatory") 
    String ingredientName;
    int quantity;
}

