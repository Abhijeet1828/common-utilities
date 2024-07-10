package com.custom.common.utilities.validators;

import org.apache.commons.lang3.StringUtils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MobileNumberValidator implements ConstraintValidator<MobileNumber, String> {

	private static final PhoneNumberUtil PHONE_NUMBER_UTIL = PhoneNumberUtil.getInstance();
	private String region;

	@Override
	public void initialize(MobileNumber constraintAnnotation) {
		this.region = constraintAnnotation.region();
	}

	@Override
	public boolean isValid(String mobileNumber, ConstraintValidatorContext context) {
		if (StringUtils.isBlank(mobileNumber)) {
			return false;
		}

		try {
			PhoneNumber parsedNumber = PHONE_NUMBER_UTIL.parse(mobileNumber, region);
			return PHONE_NUMBER_UTIL.isValidNumberForRegion(parsedNumber, region);
		} catch (NumberParseException e) {
			log.error("Error parsing mobile number", e.getMessage());
			return false;
		}
	}

}
