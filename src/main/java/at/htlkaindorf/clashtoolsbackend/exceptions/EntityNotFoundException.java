package at.htlkaindorf.clashtoolsbackend.exceptions;

/**
 * Exception thrown when an entity is not found.
 * This exception is used to indicate that a requested entity does not exist in the database.
 * It provides more specific error information than a generic IllegalArgumentException.
 */
public class EntityNotFoundException extends RuntimeException {

    /**
     * Constructs a new EntityNotFoundException with the specified entity type and ID.
     *
     * @param entityType The type of entity that was not found
     * @param id The ID of the entity that was not found
     */
    public EntityNotFoundException(String entityType, Long id) {
        super(String.format("%s with id %d not found", entityType, id));
    }

    /**
     * Constructs a new EntityNotFoundException with the specified entity type and name.
     *
     * @param entityType The type of entity that was not found
     * @param name The name of the entity that was not found
     */
    public EntityNotFoundException(String entityType, String name) {
        super(String.format("%s with name '%s' not found", entityType, name));
    }

    /**
     * Constructs a new EntityNotFoundException with the specified message.
     *
     * @param message The detail message
     */
    public EntityNotFoundException(String message) {
        super(message);
    }
}
