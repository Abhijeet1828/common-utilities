package com.custom.common.utilities.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = OptionalPhoneNumberValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface OptionalMobileNumber {

	String message() default "Invalid phone number : Please enter a valid 10 digit phone number!";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
