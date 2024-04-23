package com.custom.common.utilities.httpclient;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import javax.net.ssl.SSLContext;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.custom.common.utilities.response.WebserviceResponse;

/**
 * This class is used to send HTTP requests to remote or internal APIs.
 * 
 * @implNote It uses {@link CloseableHttpClient}
 * 
 * @author Abhijeet
 *
 */
public final class HttpUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);

	private HttpUtils() {
		throw new IllegalStateException("HttpUtils class cannot be instantiated");
	}

	/**
	 * This method is used to create a HTTP client with SSL context.
	 * 
	 * @return {@link CloseableHttpClient}
	 * 
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyStoreException
	 */
	private static CloseableHttpClient getHttpsClient()
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

		SSLContext sslContext = SSLContextBuilder.create().loadTrustMaterial(new TrustSelfSignedStrategy()).build();

		SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext);
		return HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory).build();
	}

	/**
	 * This method is used to send a GET request to an external URL.
	 * 
	 * @param url     - Complete URL of API to be called
	 * @param headers - Map of headers required to be sent
	 * 
	 * @return {@link WebserviceResponse}
	 */
	public static WebserviceResponse sendGetRequest(String url, Map<String, Object> headers) {
		WebserviceResponse webServiceResponse = null;
		try (CloseableHttpClient httpClient = getHttpsClient()) {
			LOGGER.info("GET REQUEST: URL - {}", url);
			HttpGet getRequest = new HttpGet(url);

			// Set Headers
			if (MapUtils.isNotEmpty(headers)) {
				getRequest.setHeaders(CommonHttpFunctions.getHeaders(headers));
			}

			HttpResponse httpResponse = httpClient.execute(getRequest);
			webServiceResponse = CommonHttpFunctions.prepareResponse(httpResponse, url);

		} catch (Exception e) {
			LOGGER.info("Exception in sendGetRequest : ", e);
		}
		return webServiceResponse;
	}

	/**
	 * This method is used to send a GET request to an external URL with parameters.
	 * 
	 * @param url     - Complete URL of API to be called
	 * @param headers - Map of headers required to be sent
	 * @param params  - List of request parameters to be sent
	 * 
	 * @return {@link WebserviceResponse}
	 */
	public static WebserviceResponse sendGetRequest(String url, Map<String, Object> headers,
			Map<String, String> params) {
		WebserviceResponse webServiceResponse = null;
		try (CloseableHttpClient httpClient = getHttpsClient()) {
			LOGGER.info("GET REQUEST: URL - {}", url);

			// Set Request Parameters
			URIBuilder builder = new URIBuilder(url);
			if (MapUtils.isNotEmpty(params)) {
				for (Entry<String, String> entry : params.entrySet()) {
					builder.setParameter(entry.getKey(), entry.getValue());
				}
			}

			HttpGet getRequest = new HttpGet(builder.build());

			// Set Headers
			if (MapUtils.isNotEmpty(headers)) {
				getRequest.setHeaders(CommonHttpFunctions.getHeaders(headers));
			}

			HttpResponse httpResponse = httpClient.execute(getRequest);
			webServiceResponse = CommonHttpFunctions.prepareResponse(httpResponse, url);

		} catch (Exception e) {
			LOGGER.info("Exception in sendGetRequest : ", e);
		}
		return webServiceResponse;
	}

	/**
	 * This method is used to send a POST request to an external URL.
	 * 
	 * @param url     - Complete URL of API to be called
	 * @param headers - Map of headers required to be sent
	 * @param request - Request body
	 * 
	 * @return {@link WebserviceResponse}
	 */
	public static WebserviceResponse sendPostRequest(String url, Map<String, Object> headers, Object request) {
		WebserviceResponse webServiceResponse = null;
		try (CloseableHttpClient httpClient = getHttpsClient()) {
			LOGGER.info("POST REQUEST: URL - {}", url);
			HttpPost postRequest = new HttpPost(url);

			// Set Request
			if (Objects.nonNull(request)) {
				postRequest.setEntity(CommonHttpFunctions.convertRequestToStringEntity(request));
			}

			// Set Headers
			if (MapUtils.isNotEmpty(headers)) {
				postRequest.setHeaders(CommonHttpFunctions.getHeaders(headers));
			}

			HttpResponse httpResponse = httpClient.execute(postRequest);
			webServiceResponse = CommonHttpFunctions.prepareResponse(httpResponse, url);

		} catch (Exception e) {
			LOGGER.info("Exception in sendPostRequest : ", e);
		}
		return webServiceResponse;
	}

	/**
	 * This method is used to send a PUT request to an external URL.
	 * 
	 * @param url     - Complete URL of API to be called
	 * @param headers - Map of headers required to be sent
	 * @param request - Request body
	 * 
	 * @return {@link WebserviceResponse}
	 */
	public static WebserviceResponse sendPutRequest(String url, Map<String, Object> headers, Object request) {
		WebserviceResponse webServiceResponse = null;
		try (CloseableHttpClient httpClient = getHttpsClient()) {
			LOGGER.info("PUT REQUEST: URL - {}", url);
			HttpPut putRequest = new HttpPut(url);

			// Set Request Body
			if (Objects.nonNull(request)) {
				putRequest.setEntity(CommonHttpFunctions.convertRequestToStringEntity(request));
			}

			// Set Headers
			if (MapUtils.isNotEmpty(headers)) {
				putRequest.setHeaders(CommonHttpFunctions.getHeaders(headers));
			}

			HttpResponse httpResponse = httpClient.execute(putRequest);
			webServiceResponse = CommonHttpFunctions.prepareResponse(httpResponse, url);

		} catch (Exception e) {
			LOGGER.info("Exception in sendPutRequest : ", e);
		}
		return webServiceResponse;
	}

	/**
	 * This method is used to send a DELETE request to an external URL.
	 * 
	 * @param url     - Complete URL of API to be called
	 * @param headers - Map of headers required to be sent
	 * 
	 * @return {@link WebserviceResponse}
	 */
	public static WebserviceResponse sendDeleteRequest(String url, Map<String, Object> headers) {
		WebserviceResponse webServiceResponse = null;
		try (CloseableHttpClient httpClient = getHttpsClient()) {
			LOGGER.info("DELETE REQUEST: URL - {}", url);
			HttpDelete deleteRequest = new HttpDelete(url);

			// Set Headers
			if (MapUtils.isNotEmpty(headers)) {
				deleteRequest.setHeaders(CommonHttpFunctions.getHeaders(headers));
			}

			HttpResponse httpResponse = httpClient.execute(deleteRequest);
			webServiceResponse = CommonHttpFunctions.prepareResponse(httpResponse, url);

		} catch (Exception e) {
			LOGGER.info("Exception in sendDeleteRequest : ", e);
		}
		return webServiceResponse;
	}

	/**
	 * This method is used to send a DELETE request to an external URL with request
	 * parameters.
	 * 
	 * @param url     - Complete URL of API to be called
	 * @param headers - Map of headers required to be sent
	 * @param params  - List of request parameters to be sent
	 * 
	 * @return {@link WebserviceResponse}
	 */
	public static WebserviceResponse sendDeleteRequest(String url, Map<String, Object> headers,
			Map<String, String> params) {
		WebserviceResponse webServiceResponse = null;
		try (CloseableHttpClient httpClient = getHttpsClient()) {
			LOGGER.info("DELETE REQUEST: URL - {}", url);

			// Set Request Parameters
			URIBuilder builder = new URIBuilder(url);
			if (MapUtils.isNotEmpty(params)) {
				for (Entry<String, String> entry : params.entrySet()) {
					builder.setParameter(entry.getKey(), entry.getValue());
				}
			}

			HttpDelete deleteRequest = new HttpDelete(builder.build());

			// Set Headers
			if (MapUtils.isNotEmpty(headers)) {
				deleteRequest.setHeaders(CommonHttpFunctions.getHeaders(headers));
			}

			HttpResponse httpResponse = httpClient.execute(deleteRequest);
			webServiceResponse = CommonHttpFunctions.prepareResponse(httpResponse, url);

		} catch (Exception e) {
			LOGGER.info("exception in sendDeleteRequest : ", e);
		}
		return webServiceResponse;
	}

	/**
	 * This method is used to send a MULTIPART POST request to an external URL with
	 * the request body and using FileInputStream.
	 * 
	 * @param url         - Complete URL of API to be called
	 * @param headers     - Map of headers required to be sent
	 * @param requestBody - Request body
	 * @param fileStream  - FileInputStream of the file to be sent
	 * @param fileName    - Name of the file
	 * 
	 * @return {@link WebserviceResponse}
	 */
	public static WebserviceResponse sendMultipartRequest(String url, Map<String, Object> headers,
			Map<String, Object> requestBody, FileInputStream fileStream, String fileName) {
		WebserviceResponse webServiceResponse = null;
		try (CloseableHttpClient httpClient = getHttpsClient()) {
			LOGGER.info("MULTIPART POST REQUEST: URL - {}", url);
			HttpPost multipartRequest = new HttpPost(url);

			MultipartEntityBuilder builder = MultipartEntityBuilder.create();

			// Set Filestream
			if (fileStream != null && StringUtils.isNotBlank(fileName)) {
				builder.addBinaryBody("file", fileStream, ContentType.DEFAULT_BINARY, fileName);
			}

			// Set Request Body
			if (MapUtils.isNotEmpty(requestBody)) {
				requestBody.forEach((key, value) -> {
					if (value != null) {
						builder.addTextBody(key, String.valueOf(value), ContentType.DEFAULT_BINARY);
					}
				});
			}

			HttpEntity multipart = builder.build();
			multipartRequest.setEntity(multipart);

			// Set Headers
			if (MapUtils.isNotEmpty(headers)) {
				multipartRequest.setHeaders(CommonHttpFunctions.getHeaders(headers));
			}
			multipartRequest.setHeader("Content-Type", ContentType.MULTIPART_FORM_DATA.toString());

			HttpResponse httpResponse = httpClient.execute(multipartRequest);
			webServiceResponse = CommonHttpFunctions.prepareResponse(httpResponse, url);

		} catch (Exception e) {
			LOGGER.info("Exception in sendMultipartRequest : ", e);
		}
		return webServiceResponse;
	}

	/**
	 * This method is used to send a MULTIPART POST request to an external URL with
	 * the request body and using File.
	 * 
	 * @param url         - Complete URL of API to be called
	 * @param headers     - Map of headers required to be sent
	 * @param requestBody - Request body
	 * @param file        - File to be sent
	 * 
	 * @return {@link WebserviceResponse}
	 */
	public static WebserviceResponse sendMultipartRequest(String url, Map<String, Object> headers,
			Map<String, String> requestBody, File file) {
		WebserviceResponse webServiceResponse = null;
		try (CloseableHttpClient httpClient = getHttpsClient()) {
			LOGGER.info("MULTIPART POST REQUEST: URL - {}", url);
			HttpPost multipartRequest = new HttpPost(url);

			MultipartEntityBuilder builder = MultipartEntityBuilder.create();

			// Set File
			if (Objects.nonNull(file)) {
				builder.addBinaryBody("file", file, ContentType.DEFAULT_BINARY, file.getName());
			}

			// Set Request Body
			if (MapUtils.isNotEmpty(requestBody)) {
				requestBody.forEach((key, value) -> {
					if (value != null) {
						builder.addTextBody(key, String.valueOf(value), ContentType.DEFAULT_BINARY);
					}
				});
			}

			HttpEntity multipart = builder.build();
			multipartRequest.setEntity(multipart);

			// Set Headers
			if (MapUtils.isNotEmpty(headers)) {
				multipartRequest.setHeaders(CommonHttpFunctions.getHeaders(headers));
			}
			multipartRequest.setHeader("Content-Type", ContentType.MULTIPART_FORM_DATA.toString());

			HttpResponse httpResponse = httpClient.execute(multipartRequest);
			webServiceResponse = CommonHttpFunctions.prepareResponse(httpResponse, url);
		} catch (Exception e) {
			LOGGER.info("Exception in sendMultipartRequest : ", e);
		}
		return webServiceResponse;
	}

}
