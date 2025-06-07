package at.htlkaindorf.clashtoolsbackend.dto.baseentity;

import at.htlkaindorf.clashtoolsbackend.pojos.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Record for creating or updating a base entity.
 * This record contains the information needed to create or update a base entity.
 *
 * @param name The name of the base entity (required)
 * @param level The level of the base entity
 * @param category The category of the base entity (required)
 */
public record BaseEntityRequestDTO(
    @NotBlank
    String name,

    Integer level,

    @NotNull(message = "Category is required")
    Category category
) {
    /**
     * Default constructor for deserialization.
     */
    public BaseEntityRequestDTO() {
        this(null, null, null);
    }
}
