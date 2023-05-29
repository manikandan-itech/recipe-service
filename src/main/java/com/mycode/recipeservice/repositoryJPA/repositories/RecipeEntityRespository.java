package com.mycode.recipeservice.repositoryJPA.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mycode.recipeservice.repositoryJPA.model.RecipeEntity;

@Repository
public interface RecipeEntityRespository extends JpaRepository<RecipeEntity, Long>, RecipeEntitySearchRepository {
}
