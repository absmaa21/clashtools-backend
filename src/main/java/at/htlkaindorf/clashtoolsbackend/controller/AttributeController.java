package at.htlkaindorf.clashtoolsbackend.controller;

import at.htlkaindorf.clashtoolsbackend.dto.attribute.AttributeRequestDTO;
import at.htlkaindorf.clashtoolsbackend.dto.attribute.AttributeResponseDTO;
import at.htlkaindorf.clashtoolsbackend.service.AttributeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Attribute Controller.
 * Provides REST API endpoints for managing attributes in the system.
 * This controller handles CRUD operations (Create, Read, Update, Delete) for attributes,
 * which represent characteristics of base entities in the Clash Tools application.
 */
@RestController
@RequestMapping("/api/attributes")
@RequiredArgsConstructor
public class AttributeController {

    private final AttributeService attributeService;

    /**
     * Get all attributes.
     * Retrieves a list of all attributes in the system.
     *
     * @return ResponseEntity containing a list of AttributeResponseDTO objects
     */
    @GetMapping
    public ResponseEntity<List<AttributeResponseDTO>> getAllAttributes() {
        List<AttributeResponseDTO> attributes = attributeService.getAll();
        return ResponseEntity.ok(attributes);
    }

    /**
     * Get attribute by ID.
     * Retrieves a specific attribute identified by its unique ID.
     *
     * @param id The unique identifier of the attribute to retrieve
     * @return ResponseEntity containing the requested AttributeResponseDTO
     * @throws IllegalArgumentException if no attribute with the given ID exists
     */
    @GetMapping("/{id}")
    public ResponseEntity<AttributeResponseDTO> getAttributeById(
            @PathVariable Long id) {
        AttributeResponseDTO attribute = attributeService.getById(id);
        return ResponseEntity.ok(attribute);
    }

    /**
     * Get attributes by attribute name ID.
     * Retrieves all attributes associated with a specific attribute name.
     *
     * @param attributeNameId The ID of the attribute name to filter by
     * @return ResponseEntity containing a list of AttributeResponseDTO objects
     */
    @GetMapping("/by-attribute-name/{attributeNameId}")
    public ResponseEntity<List<AttributeResponseDTO>> getAttributesByAttributeNameId(
            @PathVariable Long attributeNameId) {
        List<AttributeResponseDTO> attributes = attributeService.getAttributesByAttributeNameId(attributeNameId);
        return ResponseEntity.ok(attributes);
    }

    /**
     * Create a new attribute.
     * Creates a new attribute with the provided information. The request is validated
     * to ensure all required fields are present and valid.
     *
     * @param request The AttributeRequestDTO containing the information for the new attribute
     * @return ResponseEntity containing the newly created AttributeResponseDTO
     * @throws jakarta.validation.ConstraintViolationException if validation fails
     * @throws IllegalArgumentException if the attribute name with the given ID does not exist
     */
    @PostMapping
    public ResponseEntity<AttributeResponseDTO> createAttribute(
            @Valid @RequestBody AttributeRequestDTO request) {
        AttributeResponseDTO attribute = attributeService.create(request);
        return ResponseEntity.ok(attribute);
    }

    /**
     * Update an attribute.
     * Updates an existing attribute identified by its unique ID with the provided information.
     * The request is validated to ensure all required fields are present and valid.
     *
     * @param id The unique identifier of the attribute to update
     * @param request The AttributeRequestDTO containing the updated information
     * @return ResponseEntity containing the updated AttributeResponseDTO
     * @throws IllegalArgumentException if no attribute with the given ID exists
     * @throws IllegalArgumentException if the attribute name with the given ID does not exist
     * @throws jakarta.validation.ConstraintViolationException if validation fails
     */
    @PutMapping("/{id}")
    public ResponseEntity<AttributeResponseDTO> updateAttribute(
            @PathVariable Long id,
            @Valid @RequestBody AttributeRequestDTO request) {
        AttributeResponseDTO attribute = attributeService.update(id, request);
        return ResponseEntity.ok(attribute);
    }

    /**
     * Delete an attribute.
     * Deletes an attribute identified by its unique ID. If the entity doesn't exist,
     * the operation still returns a successful response.
     *
     * @param id The unique identifier of the attribute to delete
     * @return ResponseEntity with no content, indicating successful deletion
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttribute(
            @PathVariable Long id) {
        attributeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
