package at.htlkaindorf.clashtoolsbackend.exceptions;

/**
 * Exception thrown when a JWT token is invalid.
 * This is used during token validation to indicate that the token has been tampered with,
 * is malformed, or has an invalid signature.
 */
public class InvalidJwtException extends RuntimeException {
    public InvalidJwtException(String message) {
        super(message);
    }

    public InvalidJwtException(String message, Throwable cause) {
        super(message, cause);
    }
}
