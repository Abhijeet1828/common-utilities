package com.custom.common.utilities.response;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class UnifiedResponse<T> {

	private Status status;

	private T responseData;

	public UnifiedResponse(int statusCode, String message, T responseData) {
		this.status = new Status(statusCode, message);
		this.responseData = responseData;
	}

	public static <K, V> UnifiedResponse<Map<K, V>> withEmptyResponse(int statusCode, String message) {
		return new UnifiedResponse<>(statusCode, message, new HashMap<>());
	}

}
