package com.custom.common.utilities.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
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
@ToString
@AllArgsConstructor
public class Status {

	private int statusCode;

	private String message;

}
