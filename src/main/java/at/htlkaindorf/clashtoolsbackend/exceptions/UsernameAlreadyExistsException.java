package at.htlkaindorf.clashtoolsbackend.exceptions;

/**
 * Exception thrown when attempting to register a user with a username that already exists.
 * This is used during the registration process to indicate that the chosen username is not available.
 */
public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException(String message) {
        super(message);
    }
}
