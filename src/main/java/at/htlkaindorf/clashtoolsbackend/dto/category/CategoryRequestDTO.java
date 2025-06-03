package at.htlkaindorf.clashtoolsbackend.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

/**
 * Data Transfer Object for creating or updating a category.
 * Contains the information needed to create or update a category in the system.
 */
@Data
public class CategoryRequestDTO {

    @NotBlank(message = "Category name is required")
    @Size(min = 1, max = 100, message = "Category name must be between 1 and 100 characters")
    private String name;

    private Set<Long> attributeNameIds;

    private Set<Long> baseEntityIds;
}
