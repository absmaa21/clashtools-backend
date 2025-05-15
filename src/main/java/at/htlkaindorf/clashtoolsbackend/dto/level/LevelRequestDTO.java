package at.htlkaindorf.clashtoolsbackend.dto.level;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class LevelRequestDTO {
    @NotNull(message = "Level is mandatory")
    @Positive(message = "Level must be positive")
    private int level;
}
