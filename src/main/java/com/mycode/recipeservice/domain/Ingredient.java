package com.mycode.recipeservice.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@Builder
public class Ingredient {
    Long ingredientId;
    String ingredientName;
    int quantity;
}

