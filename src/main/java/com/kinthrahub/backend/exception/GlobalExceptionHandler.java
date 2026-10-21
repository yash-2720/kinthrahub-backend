package com.kinthrahub.backend.exception;

import java.time.LocalDateTime;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.BadCredentialsException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(AuthorizationDeniedException.class)
	public ResponseEntity<ErrorResponse> handleAuthorizationDeniedException(AuthorizationDeniedException exception,
			HttpServletRequest request) {

		ErrorResponse errorResponse = createErrorResponse(HttpStatus.FORBIDDEN, exception.getMessage(), request);

		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception,
			HttpServletRequest request) {

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(createErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage(), request));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException exception,
			HttpServletRequest request) {

		Map<String, String> errors = new HashMap<>();

		exception.getBindingResult().getFieldErrors()
				.forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));

		ErrorResponse errorResponse = createErrorResponse(HttpStatus.BAD_REQUEST, "Validation failed", request);

		errorResponse.setErrors(errors);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception exception, HttpServletRequest request) {

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.", request));
	}

	@ExceptionHandler(ResourceAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleResourceAlreadyExistsException(ResourceAlreadyExistsException exception,
			HttpServletRequest request) {

		ErrorResponse errorResponse = createErrorResponse(HttpStatus.CONFLICT, exception.getMessage(), request);

		return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(BusinessValidationException.class)
	public ResponseEntity<ErrorResponse> handleBusinessValidationException(BusinessValidationException exception,
			HttpServletRequest request) {

		ErrorResponse errorResponse = createErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage(), request);

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	private ErrorResponse createErrorResponse(HttpStatus status, String message, HttpServletRequest request) {

		ErrorResponse errorResponse = new ErrorResponse();

		errorResponse.setTimestamp(LocalDateTime.now());
		errorResponse.setStatus(status);
		errorResponse.setError(status.getReasonPhrase());
		errorResponse.setMessage(message);
		errorResponse.setPath(request.getRequestURI());

		return errorResponse;
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(
			HttpMessageNotReadableException exception, HttpServletRequest request) {

		String message = "Invalid request payload.";

		if (exception.getCause() instanceof InvalidFormatException invalidFormatException) {

			Class<?> targetType = invalidFormatException.getTargetType();

			if (targetType.isEnum()) {

				String invalidValue = String.valueOf(invalidFormatException.getValue());

				String allowedValues = Arrays.stream(targetType.getEnumConstants()).map(Object::toString)
						.collect(Collectors.joining(", "));

				message = String.format("Invalid value '%s'. Allowed values are: %s.", invalidValue, allowedValues);
			}
		}

		ErrorResponse errorResponse = createErrorResponse(HttpStatus.BAD_REQUEST, message, request);

		return ResponseEntity.badRequest().body(errorResponse);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorResponse> handleBadCredentialsException(
	        BadCredentialsException exception,
	        HttpServletRequest request) {

	    ErrorResponse errorResponse = createErrorResponse(
	            HttpStatus.UNAUTHORIZED,
	            "Invalid username or password.",
	            request
	    );

	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
	}

}