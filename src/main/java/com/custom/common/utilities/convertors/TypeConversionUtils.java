package com.custom.common.utilities.convertors;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public final class TypeConversionUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(TypeConversionUtils.class);
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
			.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
			.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

	private TypeConversionUtils() {
		throw new IllegalStateException("TypeConversionUtils class cannot be instantiated");
	}

	/**
	 * This method is used to convert Object to Custom Class provided in the
	 * parameters.
	 * 
	 * @param data
	 * @param clazz
	 * @return
	 */
	public static <T> T convertToCustomClass(Object data, Class<T> clazz) {
		try {
			if (Objects.nonNull(data) && Objects.nonNull(clazz)) {
				if (data instanceof String s) {
					return OBJECT_MAPPER.readValue(s, clazz);
				} else {
					return OBJECT_MAPPER.convertValue(data, clazz);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception in convertToCustomClass", e);
		}
		return null;
	}

	/**
	 * This method is used to convert Object to List of Custom Class Objects
	 * provided in the parameters.
	 * 
	 * @param data
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> convertToListOfObjects(Object data, Class<T> clazz) {
		try {
			if (Objects.nonNull(data) && Objects.nonNull(clazz)) {
				JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructCollectionLikeType(List.class, clazz);
				if (data instanceof String s) {
					return OBJECT_MAPPER.readValue(s, javaType);
				} else {
					return OBJECT_MAPPER.convertValue(data, javaType);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception in convertToListOfObjects", e);
		}
		return Collections.emptyList();
	}

	/**
	 * This method is used to convert Object to Map of Custom Class Objects provided
	 * in the parameters.
	 * 
	 * @param data
	 * @param valueClazz
	 * @return
	 */
	public static <T> Map<T, T> convertToCustomValueMap(Object data, Class<T> valueClazz) {
		try {
			if (Objects.nonNull(data)) {
				JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructMapLikeType(Map.class, String.class,
						valueClazz);
				if (data instanceof String s) {
					return OBJECT_MAPPER.readValue(s, javaType);
				} else {
					return OBJECT_MAPPER.convertValue(data, javaType);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception in convertToCustomValueMap", e);
		}
		return Collections.emptyMap();
	}

	/**
	 * This method is used to convert Object to Map of Custom Key and Custom Class
	 * value Objects provided in the parameters.
	 * 
	 * @param data
	 * @param keyClass
	 * @param valueClass
	 * @return
	 */
	public static <K, V> Map<K, V> convertToCustomKeyValueMap(Object data, Class<K> keyClass, Class<V> valueClass) {
		try {
			if (Objects.nonNull(data)) {
				JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructMapLikeType(Map.class, keyClass,
						valueClass);
				if (data instanceof String s) {
					return OBJECT_MAPPER.readValue(s, javaType);
				} else {
					return OBJECT_MAPPER.convertValue(data, javaType);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception in convertToCustomValueMap", e);
		}
		return Collections.emptyMap();
	}

	/**
	 * This method is used to convert Object to Map of String and Object.
	 * 
	 * @param data
	 * @return
	 */
	public static Map<String, Object> convertToMap(Object data) {
		try {
			if (Objects.nonNull(data)) {
				JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructMapLikeType(Map.class, String.class,
						Object.class);
				if (data instanceof String s) {
					return OBJECT_MAPPER.readValue(s, javaType);
				} else {
					return OBJECT_MAPPER.convertValue(data, javaType);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception in convertToMap", e);
		}
		return Collections.emptyMap();
	}

	public static Map<String, String> convertToStringkeyValueMap(Object data) {
		try {
			if (Objects.nonNull(data)) {
				JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructMapLikeType(Map.class, String.class,
						String.class);
				if (data instanceof String s) {
					return OBJECT_MAPPER.readValue(s, javaType);
				} else {
					return OBJECT_MAPPER.convertValue(data, javaType);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception in convertToStringkeyValueMap", e);
		}
		return Collections.emptyMap();
	}

	public static String convertObjectToString(Object data) {
		try {
			if (Objects.nonNull(data)) {
				return OBJECT_MAPPER.writeValueAsString(data);
			}
		} catch (Exception e) {
			LOGGER.error("Exception in convertObjectToString", e);
		}
		return StringUtils.EMPTY;
	}

	public static List<Map<String, Object>> convertToListOfMap(Object data) {
		try {
			if (Objects.nonNull(data)) {
				JavaType javaMapType = OBJECT_MAPPER.getTypeFactory().constructMapLikeType(Map.class, String.class,
						Object.class);
				JavaType javaListType = OBJECT_MAPPER.getTypeFactory().constructCollectionLikeType(List.class,
						javaMapType);
				if (data instanceof String s) {
					return OBJECT_MAPPER.readValue(s, javaListType);
				} else {
					return OBJECT_MAPPER.convertValue(data, javaListType);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception in convertToListOfMap", e);
		}
		return Collections.emptyList();
	}

}
