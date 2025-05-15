package at.htlkaindorf.clashtoolsbackend.exceptions;

/**
 * Exception thrown when a required role is not found in the database.
 * This is used during user registration or role assignment to indicate that a necessary role does not exist.
 */
public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String message) {
        super(message);
    }
}
