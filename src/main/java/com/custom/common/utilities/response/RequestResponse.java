package com.custom.common.utilities.response;

import com.custom.common.utilities.filters.RequestResponseLoggingFilter;
import com.google.gson.JsonObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class is used to print the request and response of each API request.
 * 
 * @implSpec It is being used by {@link RequestResponseLoggingFilter}.
 * 
 * @author Abhijeet
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestResponse {

	private String path;

	private JsonObject headers;

	private String queryString;

	private String httpMethod;

	private String payload;

	private int httpStatus;

	private String response;

	private long timeTakenMillis;

	@Override
	public String toString() {
		return "{ \"" + "timeTakenMillis" + "\":" + timeTakenMillis + ", \"" + "path\": \""
				+ (path != null && !path.trim().isEmpty() ? path : "") + "\", \"" + "queryString\": \""
				+ (queryString != null && !queryString.trim().isEmpty() ? queryString : "") + "\", \"" + "headers\": "
				+ (headers != null && !headers.isJsonNull() ? headers : "{}") + ", \"" + "httpMethod\": \""
				+ (httpMethod != null && !httpMethod.trim().isEmpty() ? httpMethod : "") + "\", \"" + "payload\": "
				+ (payload != null && !payload.trim().isEmpty() ? payload : "{}") + ", \"" + "httpStatus\": \""
				+ httpStatus + "\", \"" + "response\": "
				+ (response != null && !response.trim().isEmpty() ? response : "{}") + "}";
	}

}