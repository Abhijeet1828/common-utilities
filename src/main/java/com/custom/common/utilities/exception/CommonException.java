package com.custom.common.utilities.exception;

public class CommonException extends Exception {

	private static final long serialVersionUID = -4040308166528955308L;

	private Integer statusCode;
	private String message;
	private String localizedMessage;

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

	public CommonException() {
		super();
	}

	public CommonException(String msg) {
		super(msg);
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
