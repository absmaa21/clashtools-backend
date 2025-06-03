package at.htlkaindorf.clashtoolsbackend.mapper;

import at.htlkaindorf.clashtoolsbackend.dto.category.CategoryRequestDTO;
import at.htlkaindorf.clashtoolsbackend.dto.category.CategoryResponseDTO;
import at.htlkaindorf.clashtoolsbackend.pojos.Category;
import org.mapstruct.Mapper;

/**
 * Mapper for converting between Category enum and DTOs.
 * Since Category is now an enum, the mapping is straightforward.
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper {

    /**
     * Convert a CategoryRequestDTO to a Category enum value
     * @param dto the request DTO
     * @return the Category enum value
     */
    default Category toEntity(CategoryRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        return dto.getCategory();
    }

    /**
     * Convert a Category enum value to a CategoryResponseDTO
     * @param category the Category enum value
     * @return the response DTO
     */
    default CategoryResponseDTO toDto(Category category) {
        if (category == null) {
            return null;
        }
        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setCategory(category);
        return dto;
    }
}
