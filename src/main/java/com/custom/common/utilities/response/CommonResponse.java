package com.custom.common.utilities.response;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class is used to send API response in a specified manner.
 * 
 * @implNote This class is used by {@link ResponseHelper} class.
 * 
 * @author Abhijeet
 *
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonResponse {

	private Status status;

	private Object responseData;

	public CommonResponse(Status status, Object responseData) {
		this.status = status;
		if (responseData == null) {
			responseData = new HashMap<>();
		}
		this.responseData = responseData;
	}

	public CommonResponse(int statusCode, String message, String localizedMessage) {
		this.status = new Status(statusCode, message, localizedMessage);
		this.responseData = new HashMap<>();
	}

	public CommonResponse(int statusCode, String message) {
		this.status = new Status(statusCode, message);
		this.responseData = new HashMap<>();
	}

}
