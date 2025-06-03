package at.htlkaindorf.clashtoolsbackend.dto.baseentity;

import at.htlkaindorf.clashtoolsbackend.pojos.Category;
import lombok.Data;

@Data
public class BaseEntityDTO {
    private Long id;

    private String name;

    private Integer level;

    private Category category;
}
