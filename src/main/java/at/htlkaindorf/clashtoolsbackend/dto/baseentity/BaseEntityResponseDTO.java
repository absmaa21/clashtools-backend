package at.htlkaindorf.clashtoolsbackend.dto.baseentity;

import at.htlkaindorf.clashtoolsbackend.pojos.BaseEntityLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntityResponseDTO {
    private Long id;

    private String name;

    private int level;

    private Integer categoryId;

    private Set<BaseEntityLevel> baseEntityLevels = new HashSet<>();
}
