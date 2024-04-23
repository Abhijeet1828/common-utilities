package com.custom.common.utilities.httpclient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.custom.common.utilities.convertors.TypeConversionUtils;
import com.custom.common.utilities.response.WebserviceResponse;

/**
 * This class contains common functions used by the Http client classes.
 * 
 * @author Abhijeet
 *
 */
public final class CommonHttpFunctions {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommonHttpFunctions.class);

	private CommonHttpFunctions() {
		throw new IllegalStateException("CommonHttpFunctions class cannot be instantiated");
	}

	/**
	 * This method is used to convert map of headers to {@link Header} for sending
	 * HTTP requests.
	 * 
	 * @param headers {@link Header}
	 * @return
	 */
	public static Header[] getHeaders(Map<String, Object> headers) {
		List<Header> headersList = new ArrayList<>();
		for (Entry<String, Object> entry : headers.entrySet()) {
			if (Objects.nonNull(entry.getValue())) {
				headersList.add(new BasicHeader(entry.getKey(), String.valueOf(entry.getValue())));

			}
		}
		return headersList.toArray(new Header[0]);
	}

	/**
	 * This method is used to conver the {@link HttpResponse} into
	 * {@link WebserviceResponse} which can be consumed by all the services.
	 * 
	 * @param httpResponse {@link HttpResponse}
	 * @param url
	 * 
	 * @return {@link WebserviceResponse}
	 * 
	 * @throws IOException
	 */
	public static WebserviceResponse prepareResponse(HttpResponse httpResponse, String url) throws IOException {
		WebserviceResponse webServiceResponse = new WebserviceResponse();
		webServiceResponse.setHttpStatus(httpResponse.getStatusLine().getStatusCode());
		if (Objects.nonNull(httpResponse.getEntity())) {
			webServiceResponse.setResponse(EntityUtils.toString(httpResponse.getEntity()));
		}

		LOGGER.info("URL = {} \nResponse = {}", url, webServiceResponse);
		return webServiceResponse;
	}

	/**
	 * This method converts the request to {@link StringEntity} which is required
	 * for sending POST, PUT, and DELETE requests.
	 * 
	 * @param request
	 * 
	 * @return {@link StringEntity}
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public static StringEntity convertRequestToStringEntity(Object request) throws UnsupportedEncodingException {
		return new StringEntity(TypeConversionUtils.convertObjectToString(request));
	}

}
