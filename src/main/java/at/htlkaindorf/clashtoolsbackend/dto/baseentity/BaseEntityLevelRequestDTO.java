package at.htlkaindorf.clashtoolsbackend.dto.baseentity;

import at.htlkaindorf.clashtoolsbackend.pojos.ResourceType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class BaseEntityLevelRequestDTO {
    @NotNull(message = "baseEntityId is mandatory")
    private Long baseEntityId;

    @NotNull(message = "levelId is mandatory")
    private Long levelId;

    private Set<Long> attributeIds;

    @NotNull(message = "resourceType is mandatory")
    private ResourceType resourceType;

    @NotNull(message = "upgradeCost is mandatory")
    private Integer upgradeCost;

    @NotNull(message = "upgradeTime is mandatory")
    private Integer upgradeTime;

    @NotNull(message = "imgPath is mandatory")
    private String imgPath;
}
