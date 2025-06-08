package at.htlkaindorf.clashtoolsbackend.dto.account;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Simplified Data Transfer Object for account entity relationships.
 * This DTO represents the relationship between an account and a base entity,
 * including the current level of the entity and any ongoing upgrade.
 * It uses only the necessary information from BaseEntity to prevent circular references.
 */
@Data
public class SimplifiedAccountEntityDTO {

    /**
     * The unique identifier of the account entity.
     */
    @NotNull
    private Long id;

    /**
     * The ID of the base entity that this account entity represents.
     */
    @NotNull
    private Long entityId;

    /**
     * The name of the base entity that this account entity represents.
     */
    private String entityName;

    /**
     * The current level of the base entity for this account.
     */
    @NotNull
    private Integer level;

    /**
     * The timestamp when the upgrade for this entity started, if any.
     * This field can be null if no upgrade is in progress.
     */
    private Long upgradeStart;
}
