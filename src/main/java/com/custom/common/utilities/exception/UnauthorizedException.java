package com.custom.common.utilities.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UnauthorizedException extends RuntimeException {

	private static final long serialVersionUID = 4694071835871315676L;

	private final Integer statusCode;

	private final String message;

}
