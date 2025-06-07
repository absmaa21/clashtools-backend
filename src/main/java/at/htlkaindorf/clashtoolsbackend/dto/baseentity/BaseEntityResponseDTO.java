package at.htlkaindorf.clashtoolsbackend.dto.baseentity;

import java.util.HashSet;
import java.util.Set;

/**
 * Record for representing a base entity response.
 * This record contains all the information about a base entity,
 * including its levels, for use in API responses.
 *
 * @param id The unique identifier of the base entity
 * @param name The name of the base entity
 * @param level The level of the base entity
 * @param categoryId The category ID of the base entity
 * @param baseEntityLevels The set of levels associated with this base entity
 */
public record BaseEntityResponseDTO(
    Long id,
    String name,
    int level,
    Integer categoryId,
    Set<SimplifiedBaseEntityLevelDTO> baseEntityLevels
) {
    /**
     * Default constructor for deserialization.
     */
    public BaseEntityResponseDTO() {
        this(null, null, 0, null, new HashSet<>());
    }
}
