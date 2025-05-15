package at.htlkaindorf.clashtoolsbackend.exceptions;

/**
 * Exception thrown when a user provides invalid credentials during authentication.
 * This is used during the login process to indicate that the provided username and password combination is incorrect.
 */
public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
