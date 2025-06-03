package at.htlkaindorf.clashtoolsbackend.dto.category;

import at.htlkaindorf.clashtoolsbackend.pojos.Category;
import lombok.Data;

/**
 * Data Transfer Object for returning category information.
 * Since Category is now an enum, this DTO only needs to contain the enum value.
 */
@Data
public class CategoryResponseDTO {
    private Category category;
}
