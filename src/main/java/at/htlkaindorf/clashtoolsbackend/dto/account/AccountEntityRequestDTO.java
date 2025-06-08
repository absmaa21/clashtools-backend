package at.htlkaindorf.clashtoolsbackend.dto.account;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Data Transfer Object for creating or updating an account entity relationship.
 * This DTO represents the relationship between an account and a base entity,
 * including the current level of the entity and any ongoing upgrade.
 */
@Data
public class AccountEntityRequestDTO {
    /**
     * The ID of the account that owns this entity.
     * This field is required.
     */
    @NotNull
    private Long accountId;

    /**
     * The ID of the base entity that this account entity represents.
     * This field is required.
     */
    @NotNull
    private Long baseEntityId;

    /**
     * The current level of the base entity for this account.
     * This field is required.
     */
    @NotNull
    private Integer currentLevel;

    /**
     * The timestamp when the upgrade for this entity started, if any.
     * This field is optional and can be null if no upgrade is in progress.
     */
    private Long upgradeStart;
}
