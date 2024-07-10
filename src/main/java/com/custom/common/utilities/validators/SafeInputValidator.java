package com.custom.common.utilities.validators;

import org.apache.commons.lang3.StringUtils;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SafeInputValidator implements ConstraintValidator<SafeInput, String> {

	private static final PolicyFactory POLICY = Sanitizers.FORMATTING.and(Sanitizers.LINKS).and(Sanitizers.BLOCKS)
			.and(Sanitizers.STYLES).and(Sanitizers.TABLES).and(Sanitizers.IMAGES);

	@Override
	public boolean isValid(String input, ConstraintValidatorContext context) {
		if (StringUtils.isBlank(input)) {
			return true;
		}

		//String sanitizedSql = input.replaceAll("(['\";])+|(--)+", " ");

		String sanitizedHtml = POLICY.sanitize(input);
		log.info(sanitizedHtml);
		return sanitizedHtml.equals(input);
	}
}
