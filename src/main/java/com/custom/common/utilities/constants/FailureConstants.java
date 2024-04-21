package com.custom.common.utilities.constants;

/**
 * Enum for storing the common failure HTTP codes and messages that can be used
 * across Spring Boot projects.
 * 
 * 
 * @author Abhijeet
 *
 */
public enum FailureConstants {

	INTERNAL_SERVER_ERROR(-1000, "Oops! Something went wrong. Please try again later"),
	METHOD_ARGUMENT_NOT_VALID_EXCEPTION(-1001, "Invalid Request! Method Argument Not Valid"),
	FILE_TYPE_EXCEPTION(-1002, "Invalid file extension! Please try again with a valid file"),
	NO_HANDLER_FOUND_EXCEPTION(-1003, "Invalid Request! No Handler Found");

	private final int failureCode;
	private final String failureMsg;

	private FailureConstants(int failureCode, String failureMsg) {
		this.failureCode = failureCode;
		this.failureMsg = failureMsg;
	}

	public int getFailureCode() {
		return failureCode;
	}

	public String getFailureMsg() {
		return failureMsg;
	}

	@Override
	public String toString() {
		return Integer.toString(failureCode) + "-" + failureMsg;
	}

}
