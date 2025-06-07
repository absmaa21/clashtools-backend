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

    /**
     * Handles EmailAlreadyExistsException.
     * This method is called when an EmailAlreadyExistsException is thrown in the application.
     * It returns a 409 CONFLICT response with details about the error.
     *
     * @param ex The EmailAlreadyExistsException that was thrown
     * @param request The HTTP request that triggered the exception
     * @return ResponseEntity containing an ErrorResponse with details about the error
     */
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex, HttpServletRequest request) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT, request, null);
    }

    /**
     * Handles validation exceptions from request body validation.
     * This method is called when a MethodArgumentNotValidException is thrown during request validation.
     * It extracts field-specific error messages and returns a 400 BAD_REQUEST response with detailed validation errors.
     *
     * @param ex The MethodArgumentNotValidException that was thrown
     * @param request The HTTP request that triggered the exception
     * @return ResponseEntity containing an ErrorResponse with validation error details
     */
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

    /**
     * Handles all unhandled exceptions.
     * This is a catch-all handler for exceptions that don't have a specific handler.
     * It logs the error and returns a 500 INTERNAL_SERVER_ERROR response.
     *
     * @param ex The Exception that was thrown
     * @param request The HTTP request that triggered the exception
     * @return ResponseEntity containing an ErrorResponse with a generic error message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex, HttpServletRequest request) {
        logger.error("Unhandled exception at path {}: {}", request.getRequestURI(), ex.getMessage(), ex);
        return buildErrorResponse("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR, request, null);
    }

    /**
     * Handles IllegalArgumentException.
     * This method is called when an IllegalArgumentException is thrown in the application.
     * It maps specific error messages to relevant fields and returns a 400 BAD_REQUEST response.
     * For authentication-related errors, it associates the error with the appropriate field
     * (username or password) to provide more context to the client.
     *
     * @param ex The IllegalArgumentException that was thrown
     * @param request The HTTP request that triggered the exception
     * @return ResponseEntity containing an ErrorResponse with details about the error
     */
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

    /**
     * Handles optimistic locking failures.
     * This method is called when an ObjectOptimisticLockingFailureException is thrown,
     * which happens when concurrent modifications to the same entity occur.
     * It logs the error and returns a 409 CONFLICT response with a user-friendly message.
     *
     * @param ex The ObjectOptimisticLockingFailureException that was thrown
     * @param request The HTTP request that triggered the exception
     * @return ResponseEntity containing an ErrorResponse with a message about the concurrent modification
     */
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

    /**
     * Handles UsernameAlreadyExistsException.
     * This method is called when a UsernameAlreadyExistsException is thrown in the application.
     * It returns a 409 CONFLICT response with details about the error.
     *
     * @param ex The UsernameAlreadyExistsException that was thrown
     * @param request The HTTP request that triggered the exception
     * @return ResponseEntity containing an ErrorResponse with details about the error
     */
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex, HttpServletRequest request) {
        Map<String, String> fieldErrors = new HashMap<>();
        fieldErrors.put("username", ex.getMessage());
        return buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT, request, fieldErrors);
    }

    /**
     * Handles InvalidCredentialsException.
     * This method is called when an InvalidCredentialsException is thrown in the application.
     * It returns a 401 UNAUTHORIZED response with details about the error.
     *
     * @param ex The InvalidCredentialsException that was thrown
     * @param request The HTTP request that triggered the exception
     * @return ResponseEntity containing an ErrorResponse with details about the error
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(InvalidCredentialsException ex, HttpServletRequest request) {
        Map<String, String> fieldErrors = new HashMap<>();
        fieldErrors.put("password", ex.getMessage());
        return buildErrorResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED, request, fieldErrors);
    }

    /**
     * Handles RoleNotFoundException.
     * This method is called when a RoleNotFoundException is thrown in the application.
     * It returns a 500 INTERNAL_SERVER_ERROR response with details about the error.
     *
     * @param ex The RoleNotFoundException that was thrown
     * @param request The HTTP request that triggered the exception
     * @return ResponseEntity containing an ErrorResponse with details about the error
     */
    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRoleNotFoundException(RoleNotFoundException ex, HttpServletRequest request) {
        logger.error("Role not found at path {}: {}", request.getRequestURI(), ex.getMessage(), ex);
        return buildErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request, null);
    }

    /**
     * Handles InvalidJwtException.
     * This method is called when an InvalidJwtException is thrown in the application.
     * It returns a 401 UNAUTHORIZED response with details about the error.
     *
     * @param ex The InvalidJwtException that was thrown
     * @param request The HTTP request that triggered the exception
     * @return ResponseEntity containing an ErrorResponse with details about the error
     */
    @ExceptionHandler(InvalidJwtException.class)
    public ResponseEntity<ErrorResponse> handleInvalidJwtException(InvalidJwtException ex, HttpServletRequest request) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED, request, null);
    }

    /**
     * Handles JwtExpiredException.
     * This method is called when a JwtExpiredException is thrown in the application.
     * It returns a 401 UNAUTHORIZED response with details about the error.
     *
     * @param ex The JwtExpiredException that was thrown
     * @param request The HTTP request that triggered the exception
     * @return ResponseEntity containing an ErrorResponse with details about the error
     */
    @ExceptionHandler(JwtExpiredException.class)
    public ResponseEntity<ErrorResponse> handleJwtExpiredException(JwtExpiredException ex, HttpServletRequest request) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED, request, null);
    }

    /**
     * Handles EntityNotFoundException.
     * This method is called when an EntityNotFoundException is thrown in the application.
     * It returns a 404 NOT_FOUND response with details about the error.
     *
     * @param ex The EntityNotFoundException that was thrown
     * @param request The HTTP request that triggered the exception
     * @return ResponseEntity containing an ErrorResponse with details about the error
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex, HttpServletRequest request) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND, request, null);
    }

    /**
     * Builds a standardized error response.
     * This helper method creates a consistent error response structure for all exception handlers.
     * It includes timestamp, HTTP status, error message, request path, and optional field-specific errors.
     *
     * @param message The main error message
     * @param status The HTTP status code to return
     * @param request The HTTP request that triggered the exception
     * @param fieldErrors Optional map of field-specific error messages (can be null)
     * @return ResponseEntity containing a fully populated ErrorResponse
     */
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
