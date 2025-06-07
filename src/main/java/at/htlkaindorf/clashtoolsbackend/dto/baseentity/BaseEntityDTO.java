package at.htlkaindorf.clashtoolsbackend.dto.baseentity;

/**
 * Record for representing a base entity.
 * This record contains the basic information about a base entity
 * for use in API responses.
 *
 * @param id The unique identifier of the base entity
 * @param name The name of the base entity
 * @param level The level of the base entity
 * @param categoryId The category ID of the base entity
 */
public record BaseEntityDTO(
    Long id,
    String name,
    Integer level,
    Integer categoryId
) {
    /**
     * Default constructor for deserialization.
     */
    public BaseEntityDTO() {
        this(null, null, null, null);
    }
}
