package at.htlkaindorf.clashtoolsbackend.dto.baseentity;

import at.htlkaindorf.clashtoolsbackend.dto.attribute.AttributeResponseDTO;
import at.htlkaindorf.clashtoolsbackend.pojos.ResourceType;
import lombok.Builder;

import java.util.Set;

/**
 * Record for representing a simplified base entity level.
 * This record contains all the information about a base entity level,
 * but without the reference back to the base entity to avoid circular references.
 *
 * @param id The unique identifier for the base entity level
 * @param level The level value for this base entity level
 * @param attributes The attributes associated with this base entity level
 * @param resourceType The type of resource required for upgrading this base entity level, represented as an integer (ordinal value of ResourceType enum)
 * @param upgradeCost The cost to upgrade to this level, in the specified resource type
 * @param upgradeTime The time required to upgrade to this level, in seconds
 * @param imgPath The path to the image representing this base entity level
 */
@Builder
public record SimplifiedBaseEntityLevelDTO(
    Long id,
    Integer level,
    Set<AttributeResponseDTO> attributes,
    Integer resourceType,
    Integer upgradeCost,
    Integer upgradeTime,
    String imgPath
) {


    /**
     * Utility method to convert the integer resourceType back to ResourceType enum.
     * This can be useful for backward compatibility with code that expects a ResourceType.
     *
     * @param resourceTypeOrdinal the ordinal value of the ResourceType
     * @return the corresponding ResourceType enum value, or null if the ordinal is null or invalid
     */
    public static ResourceType getResourceTypeFromOrdinal(Integer resourceTypeOrdinal) {
        if (resourceTypeOrdinal == null) {
            return null;
        }
        try {
            return ResourceType.values()[resourceTypeOrdinal];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }
}
