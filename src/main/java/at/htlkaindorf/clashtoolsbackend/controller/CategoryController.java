package at.htlkaindorf.clashtoolsbackend.controller;

import at.htlkaindorf.clashtoolsbackend.dto.category.CategoryResponseDTO;
import at.htlkaindorf.clashtoolsbackend.mapper.CategoryMapper;
import at.htlkaindorf.clashtoolsbackend.pojos.Category;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for handling Category-related endpoints.
 * Since Category is now an enum, this controller only provides endpoints for retrieving all categories.
 */
@RestController
@RequestMapping("/api/categories")
@Tag(name = "Categories", description = "Endpoints for managing categories")
public class CategoryController {

    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryController(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    /**
     * Get all available categories
     * @return a list of all categories
     */
    @GetMapping
    @Operation(summary = "Get all categories", description = "Returns a list of all available categories")
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
        List<CategoryResponseDTO> categories = Arrays.stream(Category.values())
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categories);
    }
}
