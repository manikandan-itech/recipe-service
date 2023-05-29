package com.mycode.recipeservice.repositoryJPA.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.mycode.recipeservice.repositoryJPA.model.RecipeEntity;

public class RecipeEntitySearchRepositoryImpl implements RecipeEntitySearchRepository{

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<RecipeEntity> searchAll(RecipeEntity recipeEntity) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RecipeEntity> criteriaQuery = criteriaBuilder.createQuery(RecipeEntity.class);

        Root<RecipeEntity> recipeEntityRoot = criteriaQuery.from(RecipeEntity.class);
        Join<Object, Object> ingredientEntityRoot = recipeEntityRoot.join("ingredient");

        Predicate predicate = createCondition(recipeEntity, recipeEntityRoot, ingredientEntityRoot, criteriaBuilder);
        criteriaQuery.where(predicate);
        criteriaQuery.distinct(true);

        TypedQuery<RecipeEntity> query = entityManager.createQuery(criteriaQuery);

        return query.getResultList();
    }

    private Predicate createCondition(RecipeEntity recipeEntity, Root<RecipeEntity> recipeEntityRoot, Join<Object, Object> ingredientEntityRoot, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        if(StringUtils.hasText(recipeEntity.getRecipeName())) {
            predicates.add(criteriaBuilder.like(recipeEntityRoot.get("recipeName"), "%" + recipeEntity.getRecipeName() +"%"));
        }

        if(StringUtils.hasText(recipeEntity.getDishType())) {
            predicates.add(criteriaBuilder.like(recipeEntityRoot.get("dishType"), "%" + recipeEntity.getDishType() +"%"));
        }

        if(recipeEntity.getServing() != null && recipeEntity.getServing() != 0) {
            predicates.add(criteriaBuilder.equal(recipeEntityRoot.get("serving"), recipeEntity.getServing()));
        }

        if(StringUtils.hasText(recipeEntity.getInstructions())) {
            predicates.add(criteriaBuilder.like(recipeEntityRoot.get("instructions"), "%" + recipeEntity.getInstructions() +"%"));
        }

        if( (!recipeEntity.getIngredient().isEmpty()) && StringUtils.hasText(getIngredientName(recipeEntity))) {
            predicates.add(criteriaBuilder.and(criteriaBuilder.like(ingredientEntityRoot.get("ingredientName"), "%" + getIngredientName(recipeEntity) +"%")));
        }
        else
        {
            predicates.add(criteriaBuilder.isNotEmpty(recipeEntityRoot.get("ingredient")));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

    }

    private String getIngredientName(RecipeEntity recipeEntity) {
        return recipeEntity.getIngredient().get(0).getIngredientName();
    }

}
