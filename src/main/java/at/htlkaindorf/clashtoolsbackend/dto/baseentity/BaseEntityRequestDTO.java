package at.htlkaindorf.clashtoolsbackend.dto.baseentity;

import at.htlkaindorf.clashtoolsbackend.pojos.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BaseEntityRequestDTO {
    @NotBlank
    private String name;

    private Integer level;

    @NotNull(message = "Category is required")
    private Category category;
}
