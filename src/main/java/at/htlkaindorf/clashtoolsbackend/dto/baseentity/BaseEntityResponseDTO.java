package at.htlkaindorf.clashtoolsbackend.dto.baseentity;

import at.htlkaindorf.clashtoolsbackend.pojos.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntityResponseDTO {
    private Long id;

    private String name;

    private int level;

    private Category category;
}
