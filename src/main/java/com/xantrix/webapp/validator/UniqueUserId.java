package com.xantrix.webapp.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.xantrix.webapp.entities.Utenti;

/**
 * Custom validation to check that the provider {@link Utenti#getUserId()} is unique.
 * 
 * @author cristian
 */
@Target({ ElementType.TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = UniqueUserIdValidator.class)
public @interface UniqueUserId {

	public String message() default "{UniqueUserId.Utenti.userId.validation}";

	public Class<?>[] groups() default {};

	public abstract Class<? extends Payload>[] payload()

	default {};
}
