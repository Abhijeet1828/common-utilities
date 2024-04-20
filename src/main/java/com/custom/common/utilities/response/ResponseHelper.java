package com.custom.common.utilities.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class ResponseHelper {

	private ResponseHelper() {
		throw new IllegalStateException("ResponseHelper class cannot be instantiated");
	}

	public static ResponseEntity<Object> generateResponse(Integer statusCode, String message, Object responseData) {
		Status statusResponse = new Status(statusCode, message);
		CommonResponse commonResponse = new CommonResponse(statusResponse, responseData);
		return new ResponseEntity<>(commonResponse, HttpStatus.OK);
	}

	public static ResponseEntity<Object> generateResponse(Integer statusCode, String message) {
		CommonResponse commonResponse = new CommonResponse(statusCode, message);
		return new ResponseEntity<>(commonResponse, HttpStatus.OK);
	}

	public static ResponseEntity<Object> generateResponse(Integer statusCode, String message, HttpStatus httpStatus) {
		CommonResponse commonResponse = new CommonResponse(statusCode, message);
		return new ResponseEntity<>(commonResponse, httpStatus);
	}

	public static ResponseEntity<Object> generateResponse(CommonResponse commonResponse, HttpStatus httpStatus) {
		return new ResponseEntity<>(commonResponse, httpStatus);
	}

}
