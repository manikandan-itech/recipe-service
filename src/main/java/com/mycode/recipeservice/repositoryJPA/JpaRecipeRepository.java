package com.mycode.recipeservice.repositoryJPA;

import static javax.transaction.Transactional.TxType.REQUIRED;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.mycode.recipeservice.core.exceptions.RecipeDataNotFoundException;
import com.mycode.recipeservice.domain.Recipe;
import com.mycode.recipeservice.repository.RecipeRespository;
import com.mycode.recipeservice.repositoryJPA.mappers.RecipeEntityMapper;
import com.mycode.recipeservice.repositoryJPA.mappers.RecipeEntityMapperImpl;
import com.mycode.recipeservice.repositoryJPA.model.RecipeEntity;
import com.mycode.recipeservice.repositoryJPA.repositories.RecipeEntityRespository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
@Transactional(REQUIRED)
public class JpaRecipeRepository implements RecipeRespository {

    private final RecipeEntityRespository recipeEntityRespository;

    private RecipeEntityMapper recipeEntityMapper = new RecipeEntityMapperImpl();

    @Override
    public Recipe saveRecipe(Recipe recipe) {
        var recipeEntity = new RecipeEntity();
        try {
            recipeEntity = recipeEntityRespository.saveAndFlush(recipeEntityMapper.asEntity(recipe));
        } catch (Exception e) {
        	e.printStackTrace();
            throw new RecipeDataNotFoundException("Recipe name already exists..");
        }
        return recipeEntityMapper.asDTO(recipeEntity);
    }

    @Override
    public List<Recipe> searchRecipe(Recipe recipe) {
        var recipeEntityList = recipeEntityRespository.searchAll(recipeEntityMapper.asEntity(recipe));
        return recipeEntityMapper.asDTOList(recipeEntityList);
    }

    @Override
    public void updateRecipe(Recipe recipe) {
        var entity  = recipeEntityRespository.findById(recipe.getRecipeId());
        if (entity == null) {
            throw new RecipeDataNotFoundException("Recipe not found");
        }
        var recipeEntity = entity.get();
        recipeEntity = recipeEntityMapper.asEntity(recipe);
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
