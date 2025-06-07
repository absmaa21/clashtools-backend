package at.htlkaindorf.clashtoolsbackend.controller;

import at.htlkaindorf.clashtoolsbackend.dto.ApiResponse;
import at.htlkaindorf.clashtoolsbackend.dto.baseentity.BaseEntityDTO;
import at.htlkaindorf.clashtoolsbackend.dto.baseentity.BaseEntityRequestDTO;
import at.htlkaindorf.clashtoolsbackend.dto.baseentity.BaseEntityResponseDTO;
import at.htlkaindorf.clashtoolsbackend.pojos.BaseEntity;
import at.htlkaindorf.clashtoolsbackend.pojos.Category;
import at.htlkaindorf.clashtoolsbackend.repositories.projections.BaseEntitySummary;
import at.htlkaindorf.clashtoolsbackend.service.BaseEntityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Base Entity Controller.
 * Provides REST API endpoints for managing base entities in the system.
 * This controller extends the CrudController to inherit common CRUD operations
 * and adds additional specialized endpoints for base entities.
 */
@RestController
@RequestMapping("/api/base-entities")
@Tag(name = "Base Entity", description = "API for managing base entities")
public class BaseEntityController extends CrudController<BaseEntity, BaseEntityDTO, BaseEntityRequestDTO, Long> {

    private final BaseEntityService baseEntityService;

    /**
     * Constructor for dependency injection.
     *
     * @param baseEntityService The service for BaseEntity operations
     */
    public BaseEntityController(BaseEntityService baseEntityService) {
        super(baseEntityService);
        this.baseEntityService = baseEntityService;
    }

    /**
     * Get all base entities with their levels.
     *
     * @return List of base entities with levels
     */
    @GetMapping("/with-levels")
    public ResponseEntity<ApiResponse<List<BaseEntityResponseDTO>>> getAllBaseEntities() {
        List<BaseEntityResponseDTO> baseEntities = baseEntityService.getAllBaseEntitiesWithLevels();
        return ResponseEntity.ok(ApiResponse.success(baseEntities));
    }

    /**
     * Get base entity by ID with its levels.
     *
     * @param id The base entity ID
     * @return The base entity with levels
     * @throws IllegalArgumentException if entity not found
     */
    @GetMapping("/with-levels/{id}")
    public ResponseEntity<ApiResponse<BaseEntityResponseDTO>> getBaseEntityById(
            @PathVariable Long id) {
        BaseEntityResponseDTO baseEntity = baseEntityService.getBaseEntityByIdWithLevels(id);
        return ResponseEntity.ok(ApiResponse.success(baseEntity));
    }


    /**
     * Get base entities by category.
     *
     * @param category The category to filter by
     * @return List of base entities in the specified category
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<BaseEntityDTO>>> getBaseEntitiesByCategory(
            @PathVariable Category category) {
        List<BaseEntityDTO> baseEntities = baseEntityService.getBaseEntitiesByCategory(category);
        return ResponseEntity.ok(ApiResponse.success(baseEntities));
    }

    /**
     * Search base entities by name (case-insensitive).
     *
     * @param name The name substring to search for
     * @return List of base entities matching the search term
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<BaseEntityDTO>>> searchBaseEntitiesByName(
            @RequestParam String name) {
        List<BaseEntityDTO> baseEntities = baseEntityService.getBaseEntitiesByNameContaining(name);
        return ResponseEntity.ok(ApiResponse.success(baseEntities));
    }

    /**
     * Get base entity summaries.
     * This endpoint uses a projection to efficiently retrieve only the necessary data
     * without loading the entire entity and its relationships.
     * It's useful for list views and search results where only basic information is needed.
     *
     * @return List of base entity summaries
     */
    @GetMapping("/summaries")
    public ResponseEntity<ApiResponse<List<BaseEntitySummary>>> getBaseEntitySummaries() {
        List<BaseEntitySummary> summaries = baseEntityService.getAllBaseEntitySummaries();
        return ResponseEntity.ok(ApiResponse.success(summaries));
    }

    /**
     * Get base entity summaries by category.
     * This endpoint uses a projection to efficiently retrieve only the necessary data
     * without loading the entire entity and its relationships.
     * It's useful for list views and search results where only basic information is needed.
     *
     * @param category The category to filter by
     * @return List of base entity summaries in the specified category
     */
    @GetMapping("/summaries/category/{category}")
    public ResponseEntity<ApiResponse<List<BaseEntitySummary>>> getBaseEntitySummariesByCategory(
            @PathVariable Category category) {
        List<BaseEntitySummary> summaries = baseEntityService.getBaseEntitySummariesByCategory(category);
        return ResponseEntity.ok(ApiResponse.success(summaries));
    }
}
