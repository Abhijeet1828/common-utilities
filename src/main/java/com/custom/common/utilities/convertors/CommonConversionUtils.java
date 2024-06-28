package com.custom.common.utilities.convertors;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public final class CommonConversionUtils {

	private CommonConversionUtils() {
		throw new IllegalStateException("CommonConversionUtils class cannot be instantiated");
	}

	/**
	 * This method converts comma separated String to List of Long {List<Long>}.
	 * Also removes trailing ',' commas and brackets '[]'. 
	 * Example: "[1,2,3]" = [1,2,3]
	 * 
	 * @param fields
	 * @return
	 */
	public static List<Long> convertFromCommaSeperatedStrings(String fields) {
		return StringUtils.isBlank(fields) ? Collections.emptyList()
				: Arrays.stream(fields.substring(1, fields.length() - 1).split(", ")).map(Long::parseLong).toList();
	}

}
