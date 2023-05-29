package com.mycode.recipeservice.api.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.mycode.recipeservice.domain.DishType;

@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = DishTypeValidator.class)
public @interface ValidateDishType {
	
	DishType[] anyOf();
	
    String message() default "Invalid DishType: It should be in {anyOf}";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};

}
