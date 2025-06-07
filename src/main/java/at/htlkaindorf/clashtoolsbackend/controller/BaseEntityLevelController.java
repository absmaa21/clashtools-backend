package at.htlkaindorf.clashtoolsbackend.controller;

import at.htlkaindorf.clashtoolsbackend.dto.ApiResponse;
import at.htlkaindorf.clashtoolsbackend.dto.baseentity.BaseEntityLevelRequestDTO;
import at.htlkaindorf.clashtoolsbackend.dto.baseentity.BaseEntityLevelResponseDTO;
import at.htlkaindorf.clashtoolsbackend.service.BaseEntityLevelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Base Entity Level Controller.
 * Provides REST API endpoints for managing base entity levels in the system.
 * This controller handles CRUD operations (Create, Read, Update, Delete) for base entity levels,
 * which represent specific levels of base entities in the Clash Tools application.
 */
@RestController
@RequestMapping("/api/base-entity-levels")
@RequiredArgsConstructor
public class BaseEntityLevelController {

    private final BaseEntityLevelService baseEntityLevelService;

    /**
     * Get all base entity levels.
     * Retrieves a list of all base entity levels in the system.
     *
     * @return ResponseEntity containing a list of BaseEntityLevelResponseDTO objects
     */
    @GetMapping
    public ResponseEntity<List<BaseEntityLevelResponseDTO>> getAllBaseEntityLevels() {
        List<BaseEntityLevelResponseDTO> baseEntityLevels = baseEntityLevelService.getAll();
        return ResponseEntity.ok(baseEntityLevels);
    }

    /**
     * Get base entity level by ID.
     * Retrieves a specific base entity level identified by its unique ID.
     *
     * @param id The unique identifier of the base entity level to retrieve
     * @return ResponseEntity containing the requested BaseEntityLevelResponseDTO
     * @throws IllegalArgumentException if no base entity level with the given ID exists
     */
    @GetMapping("/{id}")
    public ResponseEntity<BaseEntityLevelResponseDTO> getBaseEntityLevelById(
            @PathVariable Long id) {
        BaseEntityLevelResponseDTO baseEntityLevel = baseEntityLevelService.getById(id);
        return ResponseEntity.ok(baseEntityLevel);
    }

    /**
     * Get base entity levels by base entity ID.
     * Retrieves all base entity levels associated with a specific base entity.
     *
     * @param baseEntityId The ID of the base entity to filter by
     * @return ResponseEntity containing a list of BaseEntityLevelResponseDTO objects
     * @throws IllegalArgumentException if no base entity with the given ID exists
     */
    @GetMapping("/base-entity/{baseEntityId}")
    public ResponseEntity<List<BaseEntityLevelResponseDTO>> getBaseEntityLevelsByBaseEntityId(
            @PathVariable Long baseEntityId) {
        List<BaseEntityLevelResponseDTO> baseEntityLevels = baseEntityLevelService.getBaseEntityLevelsByBaseEntityId(baseEntityId);
        return ResponseEntity.ok(baseEntityLevels);
    }



    /**
     * Create a new base entity level.
     * Creates a new base entity level with the provided information. The request is validated
     * to ensure all required fields are present and valid.
     *
     * @param requestDTO The BaseEntityLevelRequestDTO containing the information for the new base entity level
     * @return ResponseEntity containing the newly created BaseEntityLevelResponseDTO
     * @throws jakarta.validation.ConstraintViolationException if validation fails
     * @throws IllegalArgumentException if the base entity or level with the given IDs do not exist
     * @throws IllegalArgumentException if any of the attributes with the given IDs do not exist
     */
    @PostMapping
    public ResponseEntity<BaseEntityLevelResponseDTO> createBaseEntityLevel(
            @Valid @RequestBody BaseEntityLevelRequestDTO requestDTO) {
        BaseEntityLevelResponseDTO baseEntityLevel = baseEntityLevelService.create(requestDTO);
        return ResponseEntity.ok(baseEntityLevel);
    }

    /**
     * Update a base entity level.
     * Updates an existing base entity level identified by its unique ID with the provided information.
     * The request is validated to ensure all required fields are present and valid.
     *
     * @param id The unique identifier of the base entity level to update
     * @param requestDTO The BaseEntityLevelRequestDTO containing the updated information
     * @return ResponseEntity containing the updated BaseEntityLevelResponseDTO
     * @throws IllegalArgumentException if no base entity level with the given ID exists
     * @throws jakarta.validation.ConstraintViolationException if validation fails
     * @throws IllegalArgumentException if the base entity or level with the given IDs do not exist
     * @throws IllegalArgumentException if any of the attributes with the given IDs do not exist
     */
    @PutMapping("/{id}")
    public ResponseEntity<BaseEntityLevelResponseDTO> updateBaseEntityLevel(
            @PathVariable Long id,
            @Valid @RequestBody BaseEntityLevelRequestDTO requestDTO) {
        BaseEntityLevelResponseDTO baseEntityLevel = baseEntityLevelService.update(id, requestDTO);
        return ResponseEntity.ok(baseEntityLevel);
    }

    /**
     * Delete a base entity level.
     * Deletes a base entity level identified by its unique ID. If the entity doesn't exist,
     * the operation still returns a successful response.
     *
     * @param id The unique identifier of the base entity level to delete
     * @return ResponseEntity with no content, indicating successful deletion
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBaseEntityLevel(
            @PathVariable Long id) {
        baseEntityLevelService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Delete base entity levels by base entity ID.
     * Deletes all base entity levels associated with a specific base entity.
     *
     * @param baseEntityId The ID of the base entity whose levels should be deleted
     * @return ResponseEntity with no content, indicating successful deletion
     * @throws IllegalArgumentException if no base entity with the given ID exists
     */
    @DeleteMapping("/base-entity/{baseEntityId}")
    public ResponseEntity<Void> deleteBaseEntityLevelsByBaseEntityId(
            @PathVariable Long baseEntityId) {
        baseEntityLevelService.deleteBaseEntityLevelsByBaseEntityId(baseEntityId);
        return ResponseEntity.noContent().build();
    }
}
