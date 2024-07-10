package com.custom.common.utilities.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResourceAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = -3672871033957551493L;

	private final Integer statusCode;

	private final String message;

}
