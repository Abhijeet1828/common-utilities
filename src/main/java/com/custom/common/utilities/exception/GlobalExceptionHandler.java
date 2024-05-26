package com.custom.common.utilities.exception;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.custom.common.utilities.constants.FailureConstants;
import com.custom.common.utilities.response.CommonResponse;
import com.custom.common.utilities.response.ResponseHelper;
import com.custom.common.utilities.response.Status;

import jakarta.servlet.http.HttpServletResponse;

/**
 * This class is used to handle the exceptions thrown by the service layer and
 * convert them into {@link CommonResponse}.
 * 
 * @implNote Extends the {@link ResponseEntityExceptionHandler}.
 * @implSpec Handles various types of exceptions including custom exceptions
 *           like {@link CommonException}.
 * 
 * @author Abhijeet
 *
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger LOGGER_LOCAL = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		var errors = new HashMap<>();
		for (var err : ex.getBindingResult().getAllErrors())
			errors.put(((FieldError) err).getField(), err.getDefaultMessage());

		return ResponseHelper.generateResponse(
				new CommonResponse(new Status(FailureConstants.METHOD_ARGUMENT_NOT_VALID_EXCEPTION.getFailureCode(),
						FailureConstants.METHOD_ARGUMENT_NOT_VALID_EXCEPTION.getFailureMsg()), errors),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CommonException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Object> handleException(HttpServletResponse response, CommonException ex) {
		LOGGER_LOCAL.error("Global Common exception handled! StatusCode = {}, Message = {}", ex.getStatusCode(),
				ex.getLocalizedMessage());

		return ResponseHelper.generateResponse(
				new CommonResponse(ex.getStatusCode(), ex.getMessage(), ex.getLocalizedMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Object> handleException(Exception ex) {
		LOGGER_LOCAL.error("Global exception handled! Message {}", ex.getMessage(), ex);

		return ResponseHelper.generateResponse(
				new CommonResponse(FailureConstants.INTERNAL_SERVER_ERROR.getFailureCode(),
						FailureConstants.INTERNAL_SERVER_ERROR.getFailureMsg(), ex.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(NullPointerException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Object> handleException(NullPointerException ex) {
		LOGGER_LOCAL.error("NullPointerException handled! Message {}", ex.getMessage(), ex);

		return ResponseHelper.generateResponse(
				new CommonResponse(FailureConstants.INTERNAL_SERVER_ERROR.getFailureCode(),
						FailureConstants.INTERNAL_SERVER_ERROR.getFailureMsg(), ex.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
