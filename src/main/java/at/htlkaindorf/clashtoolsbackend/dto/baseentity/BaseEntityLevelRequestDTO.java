package at.htlkaindorf.clashtoolsbackend.dto.baseentity;

import at.htlkaindorf.clashtoolsbackend.pojos.ResourceType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.util.Set;

/**
 * Data Transfer Object for creating or updating a BaseEntityLevel.
 * This DTO contains all the necessary information to create or update a base entity level,
 * including its relationships, resource requirements, and upgrade details.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntityLevelRequestDTO {
    /**
     * The ID of the base entity that this level belongs to.
     */
    @NotNull(message = "baseEntityId is mandatory")
    private Long baseEntityId;

    /**
     * The ID of the level for this base entity level.
     */
    @NotNull(message = "levelId is mandatory")
    private Long levelId;

    /**
     * The IDs of the attributes associated with this base entity level.
     */
    private Set<Long> attributeIds;

    /**
     * The type of resource required for upgrading this base entity level.
     */
    @NotNull(message = "resourceType is mandatory")
    private ResourceType resourceType;

    /**
     * The cost to upgrade to this level, in the specified resource type.
     * Must be a positive number.
     */
    @NotNull(message = "upgradeCost is mandatory")
    @Min(value = 0, message = "upgradeCost must be a positive number")
    private Integer upgradeCost;

    /**
     * The time required to upgrade to this level, in seconds.
     * Must be a positive number.
     */
    @NotNull(message = "upgradeTime is mandatory")
    @Min(value = 0, message = "upgradeTime must be a positive number")
    private Integer upgradeTime;

    /**
     * The path to the image representing this base entity level.
     * This can be a relative path within the application or a full URL.
     * The path should be properly formatted and not contain directory traversal sequences.
     */
    @NotBlank(message = "imgPath is mandatory and cannot be blank")
    @URL(message = "imgPath must be a valid URL")
    private String imgPath;
}
