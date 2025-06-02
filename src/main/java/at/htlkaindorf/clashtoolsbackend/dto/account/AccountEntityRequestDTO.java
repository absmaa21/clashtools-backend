package at.htlkaindorf.clashtoolsbackend.dto.account;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Data Transfer Object for creating or updating an AccountEntity.
 * Contains the necessary fields for creating or updating an AccountEntity.
 */
@Data
public class AccountEntityRequestDTO {
    @NotNull
    private Long accountId;

    @NotNull
    private Long baseEntityId;

    @NotNull
    private Integer currentLevel;

    private Integer upgradeStart;
}
