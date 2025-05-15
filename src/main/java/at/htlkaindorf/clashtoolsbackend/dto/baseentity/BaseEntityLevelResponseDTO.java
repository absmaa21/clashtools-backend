package at.htlkaindorf.clashtoolsbackend.dto.baseentity;

import at.htlkaindorf.clashtoolsbackend.dto.attribute.AttributeResponseDTO;
import at.htlkaindorf.clashtoolsbackend.dto.level.LevelResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntityLevelResponseDTO {
    private Long id;

    private BaseEntityResponseDTO baseEntity;

    private LevelResponseDTO level;

    private Set<AttributeResponseDTO> attributes;
}
