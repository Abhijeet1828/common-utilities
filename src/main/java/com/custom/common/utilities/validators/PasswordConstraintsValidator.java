package com.custom.common.utilities.validators;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.Rule;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordConstraintsValidator implements ConstraintValidator<Password, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		List<Rule> passwordRules = new ArrayList<>();

		passwordRules.add(new LengthRule(8, 128));
		passwordRules.add(new CharacterRule(EnglishCharacterData.UpperCase, 1));
		passwordRules.add(new CharacterRule(EnglishCharacterData.LowerCase, 1));
		passwordRules.add(new CharacterRule(EnglishCharacterData.Digit, 1));
		passwordRules.add(new CharacterRule(EnglishCharacterData.Special, 1));
		passwordRules.add(new WhitespaceRule());

		PasswordValidator passwordValidator = new PasswordValidator(passwordRules);

		RuleResult ruleResult = passwordValidator.validate(new PasswordData(value));

		if (ruleResult.isValid()) {
			return true;
		}

		context.buildConstraintViolationWithTemplate(
				passwordValidator.getMessages(ruleResult).stream().findFirst().orElse(StringUtils.EMPTY)).addConstraintViolation()
				.disableDefaultConstraintViolation();
		return false;
	}

}
