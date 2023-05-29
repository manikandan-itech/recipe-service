package com.mycode.recipeservice.api.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ServingValidator implements ConstraintValidator<ValidateServing, Long>{

	@Override
	public boolean isValid(Long serving, ConstraintValidatorContext context) {
		 return serving == null ? false : true;
	}
	

}
