package com.mycode.recipeservice.core.exceptions;

public class RecipeDataNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public RecipeDataNotFoundException(String message) {
        super(message);
    }
}
