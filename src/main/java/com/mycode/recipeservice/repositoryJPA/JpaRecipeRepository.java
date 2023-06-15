package com.mycode.recipeservice.repositoryJPA;

import static javax.transaction.Transactional.TxType.REQUIRED;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.mycode.recipeservice.core.exceptions.RecipeDataNotFoundException;
import com.mycode.recipeservice.domain.Recipe;
import com.mycode.recipeservice.repository.RecipeRespository;
import com.mycode.recipeservice.repositoryJPA.mappers.FromRecipeEntityMapper;
import com.mycode.recipeservice.repositoryJPA.mappers.ToIngredientEntityMapper;
import com.mycode.recipeservice.repositoryJPA.mappers.ToRecipeEntityMapper;
import com.mycode.recipeservice.repositoryJPA.model.RecipeEntity;
import com.mycode.recipeservice.repositoryJPA.repositories.RecipeEntityRespository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
@Transactional(REQUIRED)
public class JpaRecipeRepository implements RecipeRespository {

    private final RecipeEntityRespository recipeEntityRespository;

    private ToRecipeEntityMapper toRecipeEntityMapper = new ToRecipeEntityMapper();
    private FromRecipeEntityMapper fromRecipeEntityMapper = new FromRecipeEntityMapper();
    private ToIngredientEntityMapper toIngredientEntityMapper = new ToIngredientEntityMapper();

    @Override
    public Recipe saveRecipe(Recipe recipe) {
        var recipeEntity = new RecipeEntity();
        try {
            recipeEntity = recipeEntityRespository.saveAndFlush(toRecipeEntityMapper.map(recipe));
        } catch (Exception e) {
            throw new RecipeDataNotFoundException("Recipe name already exists..");
        }
        return fromRecipeEntityMapper.map(recipeEntity);
    }

    @Override
    public List<Recipe> searchRecipe(Recipe recipe) {
        var recipeEntityList = recipeEntityRespository.searchAll(toRecipeEntityMapper.map(recipe));
        return fromRecipeEntityMapper.map(recipeEntityList);
    }

    @Override
    public void updateRecipe(Recipe recipe) {
        var entity  = recipeEntityRespository.findById(recipe.getRecipeId());
        if (entity == null) {
            throw new RecipeDataNotFoundException("Recipe not found");
        }
        var recipeEntity = entity.get();
        recipeEntity.setRecipeName(recipe.getRecipeName());
        recipeEntity.setInstructions(recipe.getInstructions());
        recipeEntity.setDishType(recipe.getDishType().name());
        recipeEntity.setServing(recipe.getServing());
        recipeEntity.setIngredient(toIngredientEntityMapper.map(recipe.getIngredients()));
        recipeEntityRespository.save(recipeEntity);
    }

    @Override
    public void deleteRecipe(Long recipeId) {
        var recipeEntity  = recipeEntityRespository.findById(recipeId);

        if (recipeEntity.isEmpty()) {
            throw new RecipeDataNotFoundException("Recipe not found");
        }
        recipeEntityRespository.delete(recipeEntity.get());
    }
}
