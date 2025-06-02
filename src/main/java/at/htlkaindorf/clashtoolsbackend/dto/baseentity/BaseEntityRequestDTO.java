package at.htlkaindorf.clashtoolsbackend.dto.baseentity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BaseEntityRequestDTO {
    @NotBlank
    private String name;

    private Integer level;
}
