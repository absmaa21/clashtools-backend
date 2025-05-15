package at.htlkaindorf.clashtoolsbackend.exceptions;

/**
 * Exception thrown when a JWT token has expired.
 * This is used during token validation to indicate that the token's expiration date has passed.
 */
public class JwtExpiredException extends RuntimeException {
    public JwtExpiredException(String message) {
        super(message);
    }

    public JwtExpiredException(String message, Throwable cause) {
        super(message, cause);
    }
}
