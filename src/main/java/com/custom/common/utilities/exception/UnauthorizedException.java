package com.custom.common.utilities.exception;

public class UnauthorizedException extends Exception {

	private static final long serialVersionUID = 4694071835871315676L;

	private final Integer statusCode;
	private final String message;
	private final String localizedMessage;

	public UnauthorizedException(Integer statusCode, String message, String localizedMessage) {
		super(message);
		this.statusCode = statusCode;
		this.message = message;
		this.localizedMessage = localizedMessage;
	}

	public UnauthorizedException(Integer statusCode, String message) {
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
