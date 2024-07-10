package com.custom.common.utilities.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Custom exception class, it is thrown when a requested resource is not found
 * in the database.
 * 
 * @implSpec Custom status code and message value included so it can be used for
 *           multiple scenarios.
 * 
 * @author Abhijeet
 *
 */
@Getter
@AllArgsConstructor
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -3672871033957551493L;

	private final Integer statusCode;

	private final String message;

}
