package at.htlkaindorf.clashtoolsbackend.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Global Exception Handler for the application.
 * This class provides centralized exception handling across all controllers in the application.
 * It translates exceptions into appropriate HTTP responses with structured error information,
 * ensuring consistent error handling and response format throughout the API.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles UserNotFoundException.
     * This method is called when a UserNotFoundException is thrown in the application.
     * It returns a 404 NOT_FOUND response with details about the error.
     *
     * @param ex The UserNotFoundException that was thrown
     * @param request The HTTP request that triggered the exception
     * @return ResponseEntity containing an ErrorResponse with details about the error
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex, HttpServletRequest request) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND, request, null);
    }

    /**
     * Handles InvalidRefreshTokenException.
     * This method is called when an InvalidRefreshTokenException is thrown in the application.
     * It returns a 401 UNAUTHORIZED response with details about the error.
     *
     * @param ex The InvalidRefreshTokenException that was thrown
     * @param request The HTTP request that triggered the exception
     * @return ResponseEntity containing an ErrorResponse with details about the error
     */
    @ExceptionHandler(InvalidRefreshTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRefreshTokenException(InvalidRefreshTokenException ex, HttpServletRequest request) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED, request, null);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex, HttpServletRequest request) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT, request, null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            fieldErrors.put(fieldName, errorMessage);
        });

        return buildErrorResponse("Validation failed", HttpStatus.BAD_REQUEST, request, fieldErrors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex, HttpServletRequest request) {
        logger.error("Unhandled exception at path {}: {}", request.getRequestURI(), ex.getMessage(), ex);
        return buildErrorResponse("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR, request, null);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        Map<String, String> fieldErrors = new HashMap<>();
        if (ex.getMessage().equals("Invalid credentials")) {
            fieldErrors.put("password", ex.getMessage());
        } else if (ex.getMessage().equals("User not found")) {
            fieldErrors.put("username", ex.getMessage());
        }
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, request, fieldErrors);
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<ErrorResponse> handleOptimisticLockingFailureException(
            ObjectOptimisticLockingFailureException ex, HttpServletRequest request) {
        logger.error("Optimistic locking failure at path {}: {}", request.getRequestURI(), ex.getMessage(), ex);
        return buildErrorResponse(
                "The resource was modified by another user. Please try again.",
                HttpStatus.CONFLICT,
                request,
                null
        );
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(String message, HttpStatus status, HttpServletRequest request, Map<String, String> fieldErrors) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(request.getRequestURI())
                .fieldErrors(fieldErrors)
                .build();

        return new ResponseEntity<>(errorResponse, status);
    }
}
