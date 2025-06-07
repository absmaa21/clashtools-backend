package at.htlkaindorf.clashtoolsbackend.controller;

import at.htlkaindorf.clashtoolsbackend.dto.ApiResponse;
import at.htlkaindorf.clashtoolsbackend.dto.category.CategoryResponseDTO;
import at.htlkaindorf.clashtoolsbackend.mapper.CategoryMapper;
import at.htlkaindorf.clashtoolsbackend.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for Category-related endpoints.
 * Provides endpoints for retrieving all categories.
 */
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Categories", description = "Endpoints for managing categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    /**
     * Get all available categories
     *
     * @return List of all categories
     */
    @GetMapping
    @Operation(summary = "Get all categories", description = "Returns a list of all available categories")
    public ResponseEntity<ApiResponse<List<CategoryResponseDTO>>> getAllCategories() {
        return ResponseEntity.ok(
                ApiResponse.success(
                        categoryService.getAllCategories().stream()
                                .map(categoryMapper::toDto)
                                .collect(Collectors.toList())
                )
        );
    }
}
