package at.htlkaindorf.clashtoolsbackend.dto.baseentity;

import at.htlkaindorf.clashtoolsbackend.dto.attribute.AttributeResponseDTO;
import at.htlkaindorf.clashtoolsbackend.pojos.ResourceType;

import java.util.HashSet;
import java.util.Set;

/**
 * Record for representing a simplified base entity level.
 * This record contains all the information about a base entity level,
 * but without the reference back to the base entity to avoid circular references.
 *
 * @param id The unique identifier for the base entity level
 * @param level The level value for this base entity level
 * @param attributes The attributes associated with this base entity level
 * @param resourceType The type of resource required for upgrading this base entity level
 * @param upgradeCost The cost to upgrade to this level, in the specified resource type
 * @param upgradeTime The time required to upgrade to this level, in seconds
 * @param imgPath The path to the image representing this base entity level
 */
public record SimplifiedBaseEntityLevelDTO(
    Long id,
    Integer level,
    Set<AttributeResponseDTO> attributes,
    ResourceType resourceType,
    Integer upgradeCost,
    Integer upgradeTime,
    String imgPath
) {
    /**
     * Default constructor for deserialization.
     */
    public SimplifiedBaseEntityLevelDTO() {
        this(null, null, new HashSet<>(), null, null, null, null);
    }

    /**
     * Static factory method to create a builder for this record.
     * This provides similar functionality to the Lombok @Builder annotation.
     *
     * @return A new builder for creating SimplifiedBaseEntityLevelDTO instances
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class for SimplifiedBaseEntityLevelDTO.
     * This provides similar functionality to the Lombok @Builder annotation.
     */
    public static class Builder {
        private Long id;
        private Integer level;
        private Set<AttributeResponseDTO> attributes = new HashSet<>();
        private ResourceType resourceType;
        private Integer upgradeCost;
        private Integer upgradeTime;
        private String imgPath;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder level(Integer level) {
            this.level = level;
            return this;
        }

        public Builder attributes(Set<AttributeResponseDTO> attributes) {
            this.attributes = attributes;
            return this;
        }

        public Builder resourceType(ResourceType resourceType) {
            this.resourceType = resourceType;
            return this;
        }

        public Builder upgradeCost(Integer upgradeCost) {
            this.upgradeCost = upgradeCost;
            return this;
        }

        public Builder upgradeTime(Integer upgradeTime) {
            this.upgradeTime = upgradeTime;
            return this;
        }

        public Builder imgPath(String imgPath) {
            this.imgPath = imgPath;
            return this;
        }

        public SimplifiedBaseEntityLevelDTO build() {
            return new SimplifiedBaseEntityLevelDTO(id, level, attributes, resourceType, upgradeCost, upgradeTime, imgPath);
        }
    }
}
