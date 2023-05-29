package com.mycode.recipeservice.api.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ServingValidator.class)
public @interface ValidateServing {
	
	String message() default "Serving is mandatory";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};

}
