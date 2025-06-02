package at.htlkaindorf.clashtoolsbackend.dto.baseentity;

import at.htlkaindorf.clashtoolsbackend.pojos.ResourceType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Data Transfer Object for creating a BaseEntityLevel with a simple request.
 * This DTO provides a simplified way to create a BaseEntityLevel and associate it with an Account.
 * It includes all necessary information for the base entity level, including resource requirements and upgrade details.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleBaseEntityLevelRequestDTO {
    /**
     * The ID of the account to associate the BaseEntityLevel with.
     */
    @NotNull(message = "Account ID is mandatory")
    private Long accountId;

    /**
     * The ID of the BaseEntityName to use for the BaseEntity.
     */
    @NotNull(message = "BaseEntityName ID is mandatory")
    private Long baseEntityNameId;

    /**
     * The ID of the Level to associate with the BaseEntityLevel.
     */
    @NotNull(message = "Level ID is mandatory")
    private Long levelId;

    /**
     * A set of Attribute IDs to associate with the BaseEntityLevel.
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
    @Pattern(regexp = "^[^.\\\\/:*?\"<>|]+(/[^.\\\\/:*?\"<>|]+)*\\.(jpg|jpeg|png|gif|webp)$|^https?://.*\\.(jpg|jpeg|png|gif|webp)$",
             message = "Image path must be a valid relative path or URL ending with a supported image extension")
    private String imgPath;
}
