package at.htlkaindorf.clashtoolsbackend.dto.level;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LevelResponseDTO {
    private Long id;

    private int level;
}
