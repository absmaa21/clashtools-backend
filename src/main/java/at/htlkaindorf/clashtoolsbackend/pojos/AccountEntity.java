package at.htlkaindorf.clashtoolsbackend.pojos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    @Schema(description = "Account that owns this account entity")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "base_entity_id", nullable = false)
    @Schema(description = "Entity corresponding to this account entity")
    private BaseEntity baseEntity;

    private Integer currentLevel;

    /**
     * Given in millis
     */
    private Integer upgradeStart;

}
