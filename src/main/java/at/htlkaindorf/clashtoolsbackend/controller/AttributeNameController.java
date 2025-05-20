package at.htlkaindorf.clashtoolsbackend.controller;

import at.htlkaindorf.clashtoolsbackend.dto.attribute.AttributeNameDTO;
import at.htlkaindorf.clashtoolsbackend.service.AttributeNameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Attribute Name Controller.
 * Provides REST API endpoints for managing attribute names in the system.
 * This controller handles CRUD operations (Create, Read, Update, Delete) for attribute names,
 * which represent the types of attributes that can be assigned to base entities in the Clash Tools application.
 */
@RestController
@RequestMapping("/api/attribute-names")
@RequiredArgsConstructor
public class AttributeNameController {

    private final AttributeNameService attributeNameService;

    /**
     * Get all attribute names.
     * Retrieves a list of all attribute names in the system.
     *
     * @return ResponseEntity containing a list of AttributeNameDTO objects
     */
    @GetMapping
    public ResponseEntity<List<AttributeNameDTO>> getAllAttributeNames() {
        List<AttributeNameDTO> attributeNames = attributeNameService.getAllAttributeNames();
        return ResponseEntity.ok(attributeNames);
    }

    /**
     * Get attribute name by ID.
     * Retrieves a specific attribute name identified by its unique ID.
     *
     * @param id The unique identifier of the attribute name to retrieve
     * @return ResponseEntity containing the requested AttributeNameDTO
     * @throws java.util.NoSuchElementException if no attribute name with the given ID exists
     */
    @GetMapping("/{id}")
    public ResponseEntity<AttributeNameDTO> getAttributeNameById(
            @PathVariable Long id) {
        AttributeNameDTO attributeName = attributeNameService.getAttributeNameById(id);
        return ResponseEntity.ok(attributeName);
    }

    /**
     * Get attribute name by name.
     * Retrieves a specific attribute name identified by its name.
     *
     * @param name The name of the attribute name to retrieve
     * @return ResponseEntity containing the requested AttributeNameDTO
     * @throws java.util.NoSuchElementException if no attribute name with the given name exists
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<AttributeNameDTO> getAttributeNameByName(
            @PathVariable String name) {
        AttributeNameDTO attributeName = attributeNameService.getAttributeNameByName(name);
        return ResponseEntity.ok(attributeName);
    }

    /**
     * Create a new attribute name.
     * Creates a new attribute name with the provided information. The request is validated
     * to ensure all required fields are present and valid.
     *
     * @param attributeNameDTO The AttributeNameDTO containing the information for the new attribute name
     * @return ResponseEntity containing the newly created AttributeNameDTO
     * @throws jakarta.validation.ConstraintViolationException if validation fails
     * @throws IllegalArgumentException if an attribute name with the same name already exists
     */
    @PostMapping
    public ResponseEntity<AttributeNameDTO> createAttributeName(
            @Valid @RequestBody AttributeNameDTO attributeNameDTO) {
        AttributeNameDTO createdAttributeName = attributeNameService.createAttributeName(attributeNameDTO);
        return ResponseEntity.ok(createdAttributeName);
    }

    /**
     * Update an attribute name.
     * Updates an existing attribute name identified by its unique ID with the provided information.
     * The request is validated to ensure all required fields are present and valid.
     *
     * @param id The unique identifier of the attribute name to update
     * @param attributeNameDTO The AttributeNameDTO containing the updated information
     * @return ResponseEntity containing the updated AttributeNameDTO
     * @throws java.util.NoSuchElementException if no attribute name with the given ID exists
     * @throws jakarta.validation.ConstraintViolationException if validation fails
     * @throws IllegalArgumentException if an attribute name with the same name already exists (and it's not the one being updated)
     */
    @PutMapping("/{id}")
    public ResponseEntity<AttributeNameDTO> updateAttributeName(
            @PathVariable Long id,
            @Valid @RequestBody AttributeNameDTO attributeNameDTO) {
        AttributeNameDTO updatedAttributeName = attributeNameService.updateAttributeName(id, attributeNameDTO);
        return ResponseEntity.ok(updatedAttributeName);
    }

    /**
     * Delete an attribute name.
     * Deletes an attribute name identified by its unique ID. If the entity doesn't exist,
     * the operation still returns a successful response.
     *
     * @param id The unique identifier of the attribute name to delete
     * @return ResponseEntity with no content, indicating successful deletion
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttributeName(
            @PathVariable Long id) {
        attributeNameService.deleteAttributeName(id);
        return ResponseEntity.noContent().build();
    }
}
