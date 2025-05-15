package at.htlkaindorf.clashtoolsbackend.dto.baseentity;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class BaseEntityLevelRequestDTO {
    @NotNull(message = "Base entity ID is mandatory")
    private Long baseEntityId;

    @NotNull(message = "Level ID is mandatory")
    private Long levelId;

    private Set<Long> attributeIds;
}
