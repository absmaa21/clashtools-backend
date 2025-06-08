package at.htlkaindorf.clashtoolsbackend.dto.account;

import at.htlkaindorf.clashtoolsbackend.dto.baseentity.SimpleBaseEntityResponseDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Data Transfer Object for account entity relationships.
 * This DTO represents the relationship between an account and a base entity,
 * including the current level of the entity and any ongoing upgrade.
 * It uses SimpleBaseEntityResponseDTO instead of BaseEntity to prevent circular references.
 * SimpleBaseEntityResponseDTO is a simplified version of BaseEntityResponseDTO without the set of levels,
 * which helps to avoid infinite loops during serialization.
 */
@Data
public class AccountEntityDTO {

    @NotNull
    private Long id;

    @NotNull
    private SimpleBaseEntityResponseDTO entity;

    @NotNull
    private Integer level;

    private Long upgradeStart;

}
