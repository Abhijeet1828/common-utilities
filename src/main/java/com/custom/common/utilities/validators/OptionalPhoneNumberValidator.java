package com.custom.common.utilities.validators;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component(value = "optionalPhoneNumberValidator")
public class OptionalPhoneNumberValidator implements ConstraintValidator<OptionalMobileNumber, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (Objects.isNull(value)) {
			return true;
		} else if (StringUtils.isBlank(value)) {
			return false;
		} else {
			return value.matches("^\\d{10}$");
		}
	}

}
