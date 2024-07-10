package com.custom.common.utilities.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = -4067691637671971127L;

	private final Integer statusCode;

	private final String message;

}
