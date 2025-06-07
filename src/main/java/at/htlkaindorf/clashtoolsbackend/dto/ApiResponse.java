package at.htlkaindorf.clashtoolsbackend.dto;

/**
 * Standard response wrapper for API responses.
 * Provides a consistent structure for all API responses.
 *
 * @param <T> Type of data contained in the response
 * @param data Data returned by the API
 * @param message Message describing the result
 * @param success Whether operation was successful
 */
public record ApiResponse<T>(
    T data,
    String message,
    boolean success
) {
    /**
     * Creates a successful response with data
     *
     * @param data Data to include in response
     * @param <T> Type of data
     * @return Successful ApiResponse with data
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data, "Success", true);
    }

    /**
     * Creates a successful response with data and custom message
     *
     * @param data Data to include in response
     * @param message Custom message describing result
     * @param <T> Type of data
     * @return Successful ApiResponse with data and message
     */
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(data, message, true);
    }

    /**
     * Creates an error response with message
     *
     * @param message Error message
     * @param <T> Type parameter (not used in error responses)
     * @return Error ApiResponse with message
     */
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(null, message, false);
    }
}
