package com.mycode.recipeservice.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.mycode.recipeservice.core.exceptions.RecipeDataNotFoundException;

class RecipeDataNotFoundExceptionTest {

    @Test
    void should_initialize_exception_correctly() {
        String message = "recipe data not found exception";

        RecipeDataNotFoundException sut = new RecipeDataNotFoundException(message);

        assertThat(sut.getMessage()).isEqualTo(message);
    }
}
