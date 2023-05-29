package com.mycode.recipeservice.repositoryJPA.repositories;

import java.util.List;

import com.mycode.recipeservice.repositoryJPA.model.RecipeEntity;

public interface RecipeEntitySearchRepository {

   List<RecipeEntity> searchAll(RecipeEntity recipeEntity);

}
