package at.htlkaindorf.clashtoolsbackend.dto.baseentity;

/**
 * Record for representing a simplified base entity response.
 * This record contains only the basic information about a base entity,
 * without the set of levels, to prevent circular references.
 *
 * @param id The unique identifier of the base entity
 * @param name The name of the base entity
 * @param level The level of the base entity
 * @param categoryId The category ID of the base entity
 */
public record SimpleBaseEntityResponseDTO(
    Long id,
    String name,
    int level,
    Integer categoryId
) {
    /**
     * Default constructor for deserialization.
     */
    public SimpleBaseEntityResponseDTO() {
        this(null, null, 0, null);
    }
}
