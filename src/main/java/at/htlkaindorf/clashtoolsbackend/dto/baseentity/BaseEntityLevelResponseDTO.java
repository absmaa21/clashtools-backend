package at.htlkaindorf.clashtoolsbackend.dto.baseentity;

import at.htlkaindorf.clashtoolsbackend.dto.attribute.AttributeResponseDTO;
import at.htlkaindorf.clashtoolsbackend.pojos.ResourceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Data Transfer Object for returning BaseEntityLevel information.
 * This DTO contains all the information about a base entity level,
 * including its relationships, resource requirements, and upgrade details.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntityLevelResponseDTO {
    /**
     * The unique identifier for the base entity level.
     */
    private Long id;

    /**
     * The base entity that this level belongs to.
     */
    private BaseEntityResponseDTO baseEntity;

    /**
     * The level value for this base entity level.
     */
    private Integer level;

    /**
     * The attributes associated with this base entity level.
     */
    private Set<AttributeResponseDTO> attributes;

    /**
     * The type of resource required for upgrading this base entity level.
     */
    private ResourceType resourceType;

    /**
     * The cost to upgrade to this level, in the specified resource type.
     */
    private Integer upgradeCost;

    /**
     * The time required to upgrade to this level, in seconds.
     */
    private Integer upgradeTime;

    /**
     * The path to the image representing this base entity level.
     * This can be a relative path within the application or a full URL.
     */
    private String imgPath;
}
