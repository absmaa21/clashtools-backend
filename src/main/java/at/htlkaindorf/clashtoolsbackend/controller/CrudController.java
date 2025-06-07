package at.htlkaindorf.clashtoolsbackend.controller;

import at.htlkaindorf.clashtoolsbackend.dto.ApiResponse;
import at.htlkaindorf.clashtoolsbackend.service.CrudService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Generic base controller for CRUD operations.
 * Provides common endpoints for Create, Read, Update, Delete operations.
 *
 * @param <E> Entity type
 * @param <D> DTO type
 * @param <R> Request DTO type
 * @param <ID> ID type
 */
@RequiredArgsConstructor
public abstract class CrudController<E, D, R, ID> {

    protected final CrudService<E, D, R, ID> service;

    /**
     * Get all entities
     *
     * @return List of all entities as DTOs
     */
    @GetMapping
    @Operation(summary = "Get all entities")
    public ResponseEntity<ApiResponse<List<D>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(service.getAll()));
    }

    /**
     * Get entity by ID
     *
     * @param id Entity identifier
     * @return Entity as DTO
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get entity by ID")
    public ResponseEntity<ApiResponse<D>> getById(@PathVariable ID id) {
        return ResponseEntity.ok(ApiResponse.success(service.getById(id)));
    }

    /**
     * Create a new entity
     *
     * @param request Entity data
     * @return Created entity as DTO
     */
    @PostMapping
    @Operation(summary = "Create a new entity")
    public ResponseEntity<ApiResponse<D>> create(@Valid @RequestBody R request) {
        D entity = service.create(request);
        return ResponseEntity.status(201)
                .body(ApiResponse.success(entity, "Entity created successfully"));
    }

    /**
     * Update an existing entity
     *
     * @param id Entity identifier
     * @param request Updated entity data
     * @return Updated entity as DTO
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing entity")
    public ResponseEntity<ApiResponse<D>> update(@PathVariable ID id, @Valid @RequestBody R request) {
        return ResponseEntity.ok(
                ApiResponse.success(service.update(id, request), "Entity updated successfully"));
    }

    /**
     * Delete an entity
     *
     * @param id Entity identifier
     * @return Success response
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an entity")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable ID id) {
        service.delete(id);
        return ResponseEntity.status(204)
                .body(ApiResponse.success(null, "Entity deleted successfully"));
    }
}
