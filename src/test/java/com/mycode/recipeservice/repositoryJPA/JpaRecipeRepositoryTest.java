package com.mycode.recipeservice.repositoryJPA;

import static com.mycode.recipeservice.domain.DishType.VEGETARIAN;
import static com.mycode.recipeservice.domain.RecipeTestFactory.recipe;
import static com.mycode.recipeservice.repositoryJPA.model.RecipeEntityTestFactory.recipeEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import com.mycode.recipeservice.core.exceptions.RecipeDataNotFoundException;
import com.mycode.recipeservice.domain.DishType;
import com.mycode.recipeservice.domain.Ingredient;
import com.mycode.recipeservice.domain.Recipe;
import com.mycode.recipeservice.repositoryJPA.mappers.FromRecipeEntityMapper;
import com.mycode.recipeservice.repositoryJPA.repositories.RecipeEntityRespository;

@DataJpaTest
@ContextConfiguration(classes = JpaRecipeRepositoryTest.TestConfig.class)
class JpaRecipeRepositoryTest {

    private static final String RECIPE_NAME = "RecipeName";
    private static final String INSTRUCTIONS = "Instructions";
    private static final Long SERVING = 4L;
    private static final String DISHTYPE = VEGETARIAN.name();
    private final FromRecipeEntityMapper fromRecipeEntityMapper = new FromRecipeEntityMapper();


    @Autowired
    private RecipeEntityRespository recipeEntityRespository;


    @Autowired
    private JpaRecipeRepository sut;

    @Test
    void test_saveRecipe() {
        var receipe = recipe();
        sut.saveRecipe(receipe);

        var result = sut.searchRecipe(receipe);
        assertThat(result).isNotEmpty();
    }

    @Test
    void when_recipe_already_exists_then_expect_exception() {
        recipeEntityRespository.saveAndFlush(recipeEntity());

        assertThatThrownBy(() -> sut.saveRecipe(recipe())) //
                .isInstanceOf(RecipeDataNotFoundException.class);
    }

    @Test
    void test_updateRecipe() {
        var recipe = saveRecipe();
        var updatedRecipe = recipe.withInstructions("Ins");
        updatedRecipe = updatedRecipe.withDishType(DishType.NON_VEGETARIAN);
        updatedRecipe = updatedRecipe.withIngredients(List.of(Ingredient.builder().ingredientName("Chicken").build()));

        sut.updateRecipe(updatedRecipe);

        var fetchRecipe = sut.searchRecipe(updatedRecipe);

        assertThat(fetchRecipe).isNotEmpty();
        assertThat(fetchRecipe.get(0).getDishType()).isEqualTo(updatedRecipe.getDishType());
        assertThat(fetchRecipe.get(0).getInstructions()).isEqualTo(updatedRecipe.getInstructions());
        assertThat(fetchRecipe.get(0).getServing()).isEqualTo(updatedRecipe.getServing());
        assertThat(fetchRecipe.get(0).getRecipeName()).isEqualTo(updatedRecipe.getRecipeName());
        assertThat(fetchRecipe.get(0).getIngredients().get(0).getIngredientName()).isEqualTo(updatedRecipe.getIngredients().get(0).getIngredientName());

    }

    @Test
    void test_deleteRecipe() {
        var recipe = recipeEntity();
        var entity = recipeEntityRespository.saveAndFlush(recipe);

        sut.deleteRecipe(entity.getRecipeId());

        var result = sut.searchRecipe(fromRecipeEntityMapper.map(entity));
        assertThat(result).isEmpty();
    }


    @Test
    void test_getRecipe() {
        var recipe = saveRecipe();

        var searchResult = sut.searchRecipe(recipe);

        assertThat(searchResult).isNotEmpty();
    }

    @DisplayName("Search receipe.")
    @ParameterizedTest(name = "search by dishType, RecipeNmae, instructions, serving {0}")
    @MethodSource("getInputArgumentsForSearchRecipe")
    void test_getRecipe(Recipe recipe) {
        var saveRecipe = saveRecipe();
        var searchResult = sut.searchRecipe(recipe);

        assertThat(searchResult).isNotEmpty();
        assertThat(searchResult).hasSize(1);
    }

    @Test
    void when_no_search_critiria_matches_then_empty_list() {
        var saveRecipe = saveRecipe();
        var updatedRecipe = saveRecipe.withDishType(DishType.NON_VEGETARIAN);

        var searchResult = sut.searchRecipe(updatedRecipe);

        assertThat(searchResult).isEmpty();
    }

    private Recipe saveRecipe() {
        var receipe = recipe();
        sut.saveRecipe(receipe);
        return receipe;
    }

    static Stream<Arguments> getInputArgumentsForSearchRecipe() {
        return Stream.of(arguments(recipe(RECIPE_NAME,null , null ,null, List.of())), //
                arguments(recipe(null,INSTRUCTIONS , null ,null, List.of())), //
                arguments(recipe(null,null , null ,DISHTYPE, List.of())), //
                arguments(recipe(null,INSTRUCTIONS , null ,null, List.of(Ingredient.builder().ingredientName("ingredient").build()))), //
                arguments(recipe(null,null , SERVING , null, List.of())));
    }

    @Configuration
    @EnableAutoConfiguration
    @Import(JpaRecipeRepository.class)
    static class TestConfig {

    }
}
