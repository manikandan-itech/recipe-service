package com.mycode.recipeservice.api.validation;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.mycode.recipeservice.domain.DishType;

public class DishTypeValidator implements ConstraintValidator<ValidateDishType, DishType>{
	
	private DishType[] dishType;
	
	@Override
    public void initialize(ValidateDishType constraint) {
        this.dishType = constraint.anyOf();
    }

	@Override
	public boolean isValid(DishType value, ConstraintValidatorContext context) {
		return value == null || Arrays.asList(DishType.values()).contains(value);
	}

	

}
