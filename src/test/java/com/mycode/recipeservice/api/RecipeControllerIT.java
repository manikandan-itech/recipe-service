package com.mycode.recipeservice.api;

import static com.mycode.recipeservice.api.ReciptDtoTestFactory.createRecipeDto;
import static com.mycode.recipeservice.domain.DishType.VEGETARIAN;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mycode.recipeservice.api.model.RecipeDto;
import com.mycode.recipeservice.core.RecipeService;
import com.mycode.recipeservice.core.exceptions.RecipeDataNotFoundException;
import com.mycode.recipeservice.domain.Recipe;
import com.mycode.recipeservice.app.RestControllerExceptionHandlerConfiguration;

import lombok.SneakyThrows;

@ContextConfiguration(classes = RecipeControllerIT.TestConfig.class)
@AutoConfigureMockMvc
@WebMvcTest
class RecipeControllerIT {

    private final ObjectMapper mapper = new ObjectMapper();

    private static final String RECIPE_NAME = "Some Str. 30";
    private static final String INSTRUCTIONS = "1211GT";
    private static final Long SERVING = 2L;
    private static final String DISHTYPE = VEGETARIAN.name();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeService recipeService;

    @Test
    void testSaveRecipe() throws Exception {
        var recipe = getRecipe();
        mockMvc.perform(MockMvcRequestBuilders.post("/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonObj(recipe))) //
                .andExpect(status().isOk());

        verify(recipeService).saveRecipe(any());
    }

    @Test
    void testUpdateRecipe() throws Exception {
        var recipe = getRecipe();
        mockMvc.perform(MockMvcRequestBuilders.put("/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonObj(recipe))) //
                .andExpect(status().isOk());

        verify(recipeService).updateRecipe(any());
    }

    @Test
    void testDeleteRecipe() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/recipes/{recipeId}",1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(recipeService).deleteRecipe(any());
    }

    @SneakyThrows
    @Test
    void when_thers_is_an_error_while_saving_recipe_then_expect_exception() {
        when(recipeService.saveRecipe(any())).thenThrow(RecipeDataNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonObj(getRecipe())))
                .andExpect(status().isBadRequest());
    }

    @SneakyThrows
    @Test
    void when_thers_is_an_error_while_delete_recipe_then_expect_exception() {
        doThrow(RecipeDataNotFoundException.class).when(recipeService).deleteRecipe(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/recipes/{recipeId}",1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("RecipeDto bean validation tests.")
    @ParameterizedTest(name = "Expected UnprocessableEntity for {0}")
    @MethodSource("getInputArgumentsForRecipe")
    void testBeanValidation(RecipeDto recipeDto) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonObj(recipeDto))) //
                .andExpect(status().isUnprocessableEntity());

        verify(recipeService, never()).saveRecipe(any());
    }

    static Stream<Arguments> getInputArgumentsForRecipe() {
        return Stream.of(arguments(createRecipeDto(RECIPE_NAME,null , SERVING ,DISHTYPE)), //
                arguments(createRecipeDto(null,INSTRUCTIONS , SERVING ,DISHTYPE)), //
                arguments(createRecipeDto(RECIPE_NAME,INSTRUCTIONS , null ,DISHTYPE)), //
                arguments(createRecipeDto(RECIPE_NAME,INSTRUCTIONS , SERVING , null)));
    }

    private Recipe getRecipe() {
        return Recipe.builder()
                .recipeName("test")
                .serving(1L)
                .dishType(VEGETARIAN)
                .instructions("testIns")
                .build();
    }

    public static String toJsonObj(Object content) throws JsonProcessingException {
        ObjectMapper obj = new ObjectMapper();
        obj.registerModule(new JavaTimeModule());
        return obj.writeValueAsString(content);
    }

    @Configuration
    @Import({ RecipeController.class, RestControllerExceptionHandlerConfiguration.class })
    static class TestConfig {
    }
}
