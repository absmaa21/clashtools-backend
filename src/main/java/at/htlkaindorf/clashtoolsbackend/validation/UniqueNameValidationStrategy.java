package at.htlkaindorf.clashtoolsbackend.validation;

import at.htlkaindorf.clashtoolsbackend.pojos.BaseEntity;
import at.htlkaindorf.clashtoolsbackend.repositories.BaseEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Validation strategy for ensuring that a BaseEntity has a unique name.
 * This strategy checks if a BaseEntity with the same name already exists in the database.
 */
@Component
@RequiredArgsConstructor
public class UniqueNameValidationStrategy implements ValidationStrategy<BaseEntity> {

    private final BaseEntityRepository repository;

    /**
     * Validates that the BaseEntity has a unique name.
     *
     * @param entity The BaseEntity to validate
     * @return true if no other BaseEntity with the same name exists, false otherwise
     */
    @Override
    public boolean isValid(BaseEntity entity) {
        if (entity == null || entity.getName() == null) {
            return false;
        }

        // If the entity has an ID, we need to exclude it from the check
        // to allow updates to existing entities
        if (entity.getId() != null) {
            return !repository.existsByNameAndIdNot(entity.getName(), entity.getId());
        }

        // For new entities, simply check if the name is already taken
        return !repository.existsByName(entity.getName());
    }

    /**
     * Gets the error message for an invalid entity.
     *
     * @return The error message
     */
    @Override
    public String getErrorMessage() {
        return "A base entity with this name already exists";
    }
}
