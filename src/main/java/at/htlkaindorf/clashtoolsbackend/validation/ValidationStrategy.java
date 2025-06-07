package at.htlkaindorf.clashtoolsbackend.validation;

/**
 * Interface for validation strategies.
 * This interface defines a common contract for validation strategies
 * that can be used to validate entities before they are persisted.
 *
 * @param <T> The type of entity to validate
 */
public interface ValidationStrategy<T> {

    /**
     * Validates the entity.
     *
     * @param entity The entity to validate
     * @return true if the entity is valid, false otherwise
     */
    boolean isValid(T entity);

    /**
     * Gets the error message for an invalid entity.
     *
     * @return The error message
     */
    String getErrorMessage();
}
