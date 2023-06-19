package com.mycode.recipeservice.api;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mycode.recipeservice.api.mappers.RecipeMapper;
import com.mycode.recipeservice.api.model.RecipeDto;
import com.mycode.recipeservice.core.RecipeService;
import com.mycode.recipeservice.domain.DishType;
import com.mycode.recipeservice.domain.Ingredient;
import com.mycode.recipeservice.domain.Recipe;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/recipes")
@AllArgsConstructor
@Slf4j 
public class RecipeController {

    private final RecipeService recipeService;
    
    private final RecipeMapper recipeMapper;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public RecipeDto saveRecipe(@Valid  @RequestBody RecipeDto recipeDto){
    	log.info("Recipe creation request recived!!!");
    	
        var recipe = recipeMapper.asEntity(recipeDto);
        var recipeSave = recipeMapper.asDTO(recipeService.saveRecipe(recipe));
        
        log.info("Recipe creation request Completed!!!");
        return recipeSave;
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public void updateRecipe(@Valid @RequestBody RecipeDto recipeDto){
    	log.info("Recipe update request recived!!!");
    	
        var recipe = recipeMapper.asEntity(recipeDto);
        recipeService.updateRecipe(recipe);
        
        log.info("Recipe update request Completed!!!");
    }

    @DeleteMapping(value = "/{recipeId}", consumes = APPLICATION_JSON_VALUE)
    public void updateRecipe(@PathVariable Long recipeId){
        recipeService.deleteRecipe(recipeId);
        log.info("{} Recipe has been delete!!!", recipeId);
    }

    @GetMapping
    public List<Recipe> searchRecipe(@RequestParam(name = "recipeName", required = false) String recipeName,
            @RequestParam(name = "serving", required = false) Long serving,
            @RequestParam(name = "dishType", required = false) DishType dishType,
            @RequestParam(name = "instructions", required = false) String instructions,
            @RequestParam(name = "ingredientName", required = false) String ingredientName){
        var recipe = createRecipe(recipeName, serving, dishType, instructions, ingredientName);
        return recipeService.searchRecipe(recipe);
    }

    private Recipe createRecipe(String recipeName, Long serving, DishType dishType, String instructions, String ingredientName) {
        return Recipe.builder()
                .recipeName(recipeName)
                .serving(serving)
                .dishType(dishType)
                .instructions(instructions)
                .ingredient(List.of(Ingredient.builder().ingredientName(ingredientName).build()))
                .build();
    }

}
