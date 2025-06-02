package at.htlkaindorf.clashtoolsbackend.dto.account;

import at.htlkaindorf.clashtoolsbackend.pojos.BaseEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccountEntityDTO {

    @NotNull
    private Long id;

    @NotNull
    private BaseEntity entity;

    @NotNull
    private Integer level;

    private Integer upgradeStart;

}
