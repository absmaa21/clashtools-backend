package at.htlkaindorf.clashtoolsbackend.dto.baseentity;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

/**
 * Data Transfer Object for creating a BaseEntityLevel with a simple request.
 * This DTO provides a simplified way to create a BaseEntityLevel and associate it with an Account.
 */
@Data
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
}
