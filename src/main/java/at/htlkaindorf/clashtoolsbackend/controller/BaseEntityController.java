package at.htlkaindorf.clashtoolsbackend.controller;

import at.htlkaindorf.clashtoolsbackend.dto.baseentity.BaseEntityDTO;
import at.htlkaindorf.clashtoolsbackend.dto.baseentity.BaseEntityRequestDTO;
import at.htlkaindorf.clashtoolsbackend.pojos.Category;
import at.htlkaindorf.clashtoolsbackend.service.BaseEntityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Base Entity Controller.
 * Provides REST API endpoints for managing base entities in the system.
 * This controller handles CRUD operations (Create, Read, Update, Delete) for base entities,
 * which are fundamental game elements in the Clash Tools application.
 */
@RestController
@RequestMapping("/api/base-entities")
@RequiredArgsConstructor
public class BaseEntityController {

    private final BaseEntityService baseEntityService;

    /**
     * Get all base entities.
     * Retrieves a list of all base entities in the system.
     *
     * @return ResponseEntity containing a list of BaseEntityDTO objects
     */
    @GetMapping
    public ResponseEntity<List<BaseEntityDTO>> getAllBaseEntities() {
        List<BaseEntityDTO> baseEntities = baseEntityService.getAllBaseEntities();
        return ResponseEntity.ok(baseEntities);
    }

    /**
     * Get base entity by ID.
     * Retrieves a specific base entity identified by its unique ID.
     *
     * @param id The unique identifier of the base entity to retrieve
     * @return ResponseEntity containing the requested BaseEntityDTO
     * @throws java.util.NoSuchElementException if no base entity with the given ID exists
     */
    @GetMapping("/{id}")
    public ResponseEntity<BaseEntityDTO> getBaseEntityById(
            @PathVariable Long id) {
        BaseEntityDTO baseEntity = baseEntityService.getBaseEntityById(id);
        return ResponseEntity.ok(baseEntity);
    }

    /**
     * Create a new base entity.
     * Creates a new base entity with the provided information. The request is validated
     * to ensure all required fields are present and valid.
     *
     * @param request The BaseEntityRequestDTO containing the information for the new base entity
     * @return ResponseEntity containing the newly created BaseEntityDTO
     * @throws jakarta.validation.ConstraintViolationException if validation fails
     */
    @PostMapping
    public ResponseEntity<BaseEntityDTO> createBaseEntity(
            @Valid @RequestBody BaseEntityRequestDTO request) {
        BaseEntityDTO baseEntity = baseEntityService.createBaseEntity(request);
        return ResponseEntity.ok(baseEntity);
    }

    /**
     * Update a base entity.
     * Updates an existing base entity identified by its unique ID with the provided information.
     * The request is validated to ensure all required fields are present and valid.
     *
     * @param id The unique identifier of the base entity to update
     * @param request The BaseEntityRequestDTO containing the updated information
     * @return ResponseEntity containing the updated BaseEntityDTO
     * @throws java.util.NoSuchElementException if no base entity with the given ID exists
     * @throws jakarta.validation.ConstraintViolationException if validation fails
     */
    @PutMapping("/{id}")
    public ResponseEntity<BaseEntityDTO> updateBaseEntity(
            @PathVariable Long id,
            @Valid @RequestBody BaseEntityRequestDTO request) {
        BaseEntityDTO baseEntity = baseEntityService.updateBaseEntity(id, request);
        return ResponseEntity.ok(baseEntity);
    }

    /**
     * Delete a base entity.
     * Deletes a base entity identified by its unique ID. If the entity doesn't exist,
     * the operation still returns a successful response.
     *
     * @param id The unique identifier of the base entity to delete
     * @return ResponseEntity with no content, indicating successful deletion
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBaseEntity(
            @PathVariable Long id) {
        baseEntityService.deleteBaseEntity(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get base entities by category.
     * Retrieves a list of base entities filtered by the specified category.
     * This endpoint uses an optimized query that leverages database indexing.
     *
     * @param category The category to filter by
     * @return ResponseEntity containing a list of BaseEntityDTO objects
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<BaseEntityDTO>> getBaseEntitiesByCategory(
            @PathVariable Category category) {
        List<BaseEntityDTO> baseEntities = baseEntityService.getBaseEntitiesByCategory(category);
        return ResponseEntity.ok(baseEntities);
    }

    /**
     * Search base entities by name.
     * Retrieves a list of base entities with names containing the specified string (case-insensitive).
     * This endpoint uses an optimized query for case-insensitive name searching.
     *
     * @param name The name substring to search for
     * @return ResponseEntity containing a list of BaseEntityDTO objects
     */
    @GetMapping("/search")
    public ResponseEntity<List<BaseEntityDTO>> searchBaseEntitiesByName(
            @RequestParam String name) {
        List<BaseEntityDTO> baseEntities = baseEntityService.getBaseEntitiesByNameContaining(name);
        return ResponseEntity.ok(baseEntities);
    }
}
