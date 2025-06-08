package at.htlkaindorf.clashtoolsbackend.exceptions;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Standard error response object for API errors.
 * This class provides a consistent structure for error responses across the application,
 * including timestamp, HTTP status, error type, message, request path, and field-specific errors.
 */
@Data
@Builder
public class ErrorResponse {
    /**
     * The timestamp when the error occurred.
     */
    private LocalDateTime timestamp;

    /**
     * The HTTP status code of the response.
     */
    private int status;

    /**
     * The type of error that occurred (e.g., "Bad Request", "Not Found").
     */
    private String error;

    /**
     * A human-readable error message describing what went wrong.
     */
    private String message;

    /**
     * The path of the request that caused the error.
     */
    private String path;

    /**
     * A map of field-specific validation errors, where the key is the field name
     * and the value is the error message for that field.
     */
    private Map<String, String> fieldErrors;
}
