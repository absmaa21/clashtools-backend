package at.htlkaindorf.clashtoolsbackend.pojos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a game entity owned by a user account.
 * This class establishes the relationship between a user's account and a base entity in the game,
 * tracking the current level and upgrade status of the entity for that specific account.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountEntity {

    /**
     * The unique identifier for this account entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /**
     * The account that owns this entity.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    @Schema(description = "Account that owns this account entity")
    private Account account;

    /**
     * The base entity that this account entity represents.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "base_entity_id", nullable = false)
    @Schema(description = "Entity corresponding to this account entity")
    private BaseEntity baseEntity;

    /**
     * The current level of this entity for the owning account.
     */
    private Integer currentLevel;

    /**
     * The timestamp when the upgrade for this entity started, if any.
     * Given in milliseconds since the epoch.
     * Null if no upgrade is in progress.
     */
    private Integer upgradeStart;

}
