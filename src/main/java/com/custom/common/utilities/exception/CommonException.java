package com.custom.common.utilities.exception;

/**
 * This class is used to create custom exceptions with HTTP status codes and
 * error messages.
 * 
 * @implNote Extends the Exception class.
 * 
 * @author Abhijeet
 *
 */
public class CommonException extends Exception {

	private static final long serialVersionUID = -4040308166528955308L;

	private final Integer statusCode;
	private final String message;
	private final String localizedMessage;

	public CommonException(Integer statusCode, String message, String localizedMessage) {
		super(message);
		this.statusCode = statusCode;
		this.message = message;
		this.localizedMessage = localizedMessage;
	}

	public CommonException(Integer statusCode, String message) {
		super(message);
		this.statusCode = statusCode;
		this.message = message;
		this.localizedMessage = message;
	}

	@Override
	public String getLocalizedMessage() {
		return this.localizedMessage;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
