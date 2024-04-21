package com.custom.common.utilities.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class is used to store the response status codes, messages and localized
 * messages.
 * 
 * @implSpec This class is used by {@link CommonResponse}.
 * 
 * @author Abhijeet
 *
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Status {

	private int statusCode;

	private String message;

	private String localizedMessage;

	public Status(int statusCode, String message, String localizedMessage) {
		this.statusCode = statusCode;
		this.message = message;
		this.localizedMessage = localizedMessage;
	}

	public Status(int statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
		this.localizedMessage = message;

	}

}
