package com.mycode.recipeservice.core;

import static com.mycode.recipeservice.domain.RecipeTestFactory.recipe;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mycode.recipeservice.domain.Recipe;
import com.mycode.recipeservice.repository.RecipeRespository;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceImplTest {

    @Mock
    private RecipeRespository recipeRespository;

    @Captor
    private ArgumentCaptor<Recipe> captorRecipe;

    @InjectMocks
    private RecipeServiceImpl sut;

    @Test
    void testSaveRecipe() {
        var recipe = recipe();

        sut.saveRecipe(recipe);

        verify(recipeRespository).saveRecipe(recipe);
    }

    @Test
    void testUpdateRecipe() {
        var recipe = recipe();

        sut.updateRecipe(recipe);

        verify(recipeRespository).updateRecipe(captorRecipe.capture());
        var result = captorRecipe.getValue();

        assertThat(result.getDishType()).isEqualTo(recipe.getDishType());
        assertThat(result.getInstructions()).isEqualTo(recipe.getInstructions());
        assertThat(result.getServing()).isEqualTo(recipe.getServing());
        assertThat(result.getRecipeName()).isEqualTo(recipe.getRecipeName());
    }

    @Test
    void testDeleteRecipe() {
        var recipe = recipe();

        sut.deleteRecipe(recipe.getRecipeId());

        verify(recipeRespository).deleteRecipe(any());
    }

    @Test
    void testGetRecipe() {
        var recipe = recipe();

        var result = sut.getRecipe(recipe);

        verify(recipeRespository).getRecipe(any());
    }

}
