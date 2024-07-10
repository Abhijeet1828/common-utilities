package com.custom.common.utilities.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.custom.common.utilities.constants.FailureConstants;
import com.custom.common.utilities.response.CommonResponse;
import com.custom.common.utilities.response.UnifiedResponse;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

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
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		var errors = new HashMap<>();
		for (var err : ex.getBindingResult().getAllErrors())
			errors.put(((FieldError) err).getField(), err.getDefaultMessage());

		return new ResponseEntity<>(
				new UnifiedResponse<>(FailureConstants.METHOD_ARGUMENT_NOT_VALID_EXCEPTION.getFailureCode(),
						FailureConstants.METHOD_ARGUMENT_NOT_VALID_EXCEPTION.getFailureMsg(), Map.of("errors", errors)),
				HttpStatus.BAD_REQUEST);
	}

	/**
	 * This method handles all the violations which happen when using path
	 * parameters or query parameters.
	 * 
	 * @param ex {@link ConstraintViolationException}
	 * 
	 * @return
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
		log.error("Global ConstraintViolationException handled! Message = {}", ex.getMessage());

		var errors = new HashMap<>();
		for (var err : ex.getConstraintViolations())
			errors.put(err.getPropertyPath().toString().split("\\.")[1], err.getMessage());

		return new ResponseEntity<>(
				new UnifiedResponse<>(FailureConstants.CONSTRAINT_VIOLATION_EXCEPTION.getFailureCode(),
						FailureConstants.CONSTRAINT_VIOLATION_EXCEPTION.getFailureMsg(), Map.of("errors", errors)),
				HttpStatus.BAD_REQUEST);
	}

	/**
	 * This exception is thrown for validation exceptions in files or other places
	 * where Unauthorized, ResourceNotFound, ResourceAlreadyExists exceptions cannot
	 * be thrown.
	 * 
	 * @apiNote These validations are manual checks and not performed by Spring or
	 *          Jakarta validations.
	 * 
	 * @param ex {@link ValidationException}
	 * 
	 * @return
	 */
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<Object> handleValidationException(ValidationException ex) {
		log.error("Global Validation exception handled! StatusCode = {}, Message = {}", ex.getStatusCode(),
				ex.getMessage());

		return new ResponseEntity<>(UnifiedResponse.withEmptyResponse(ex.getStatusCode(), ex.getMessage()),
				HttpStatus.BAD_REQUEST);
	}

	/**
	 * This exception is thrown when a user tries to perform an action which they
	 * are not authorized for.
	 * 
	 * @param response
	 * 
	 * @param ex       {@link UnauthorizedException}
	 * 
	 * @return
	 */
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException ex) {
		log.error("Global Unauthorized exception handled! StatusCode = {}, Message = {}", ex.getStatusCode(),
				ex.getLocalizedMessage());

		return new ResponseEntity<>(UnifiedResponse.withEmptyResponse(ex.getStatusCode(), ex.getMessage()),
				HttpStatus.UNAUTHORIZED);
	}

	/**
	 * This method handles the custom exception which is thrown when a requested
	 * resource is not found in the database.
	 * 
	 * @param ex {@link ResourceNotFoundException}
	 * 
	 * @return
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
		log.error("Global ResourceNotFoundException handled! StatusCode = {}, Message = {}", ex.getStatusCode(),
				ex.getLocalizedMessage());

		return new ResponseEntity<>(UnifiedResponse.withEmptyResponse(ex.getStatusCode(), ex.getMessage()),
				HttpStatus.NOT_FOUND);
	}

	/**
	 * This method handles the custom exception which is thrown when a user already
	 * exists in the database, while signing up.
	 * 
	 * @param ex {@link ResourceAlreadyExistsException}
	 * 
	 * @return
	 */
	@ExceptionHandler(ResourceAlreadyExistsException.class)
	public ResponseEntity<Object> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
		log.error("Global ResourceAlreadyExistsException handled! StatusCode = {}, Message = {}", ex.getStatusCode(),
				ex.getMessage());

		return new ResponseEntity<>(UnifiedResponse.withEmptyResponse(ex.getStatusCode(), ex.getMessage()),
				HttpStatus.CONFLICT);
	}

	/**
	 * This method handles all the exceptions which are not handled seperately in
	 * the above methods.
	 * 
	 * @param ex {@link Exception}
	 * 
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleException(Exception ex) {
		log.error("Global exception handled! Message {}", ex.getMessage(), ex);

		return new ResponseEntity<>(
				UnifiedResponse.withEmptyResponse(FailureConstants.INTERNAL_SERVER_ERROR.getFailureCode(),
						FailureConstants.INTERNAL_SERVER_ERROR.getFailureMsg()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
