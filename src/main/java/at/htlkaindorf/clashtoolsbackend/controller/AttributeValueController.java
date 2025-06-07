package at.htlkaindorf.clashtoolsbackend.controller;

import at.htlkaindorf.clashtoolsbackend.dto.ApiResponse;
import at.htlkaindorf.clashtoolsbackend.dto.attribute.AttributeValueDTO;
import at.htlkaindorf.clashtoolsbackend.service.AttributeValueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Attribute Value Controller.
 * Provides REST API endpoints for managing attribute values in the system.
 * This controller handles CRUD operations (Create, Read, Update, Delete) for attribute values,
 * which represent the values of attributes for specific base entity levels in the Clash Tools application.
 */
@RestController
@RequestMapping("/api/attribute-values")
@RequiredArgsConstructor
public class AttributeValueController {

    private final AttributeValueService attributeValueService;

    /**
     * Get all attribute values.
     * Retrieves a list of all attribute values in the system.
     *
     * @return ResponseEntity containing a list of AttributeValueDTO objects
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<AttributeValueDTO>>> getAllAttributeValues() {
        List<AttributeValueDTO> attributeValues = attributeValueService.getAllAttributeValues();
        return ResponseEntity.ok(ApiResponse.success(attributeValues));
    }

    /**
     * Get attribute value by ID.
     * Retrieves a specific attribute value identified by its unique ID.
     *
     * @param id The unique identifier of the attribute value to retrieve
     * @return ResponseEntity containing the requested AttributeValueDTO
     * @throws IllegalArgumentException if no attribute value with the given ID exists
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AttributeValueDTO>> getAttributeValueById(
            @PathVariable Long id) {
        AttributeValueDTO attributeValue = attributeValueService.getAttributeValueById(id);
        return ResponseEntity.ok(ApiResponse.success(attributeValue));
    }

    /**
     * Get attribute values by attribute ID.
     * Retrieves all attribute values associated with a specific attribute.
     *
     * @param attributeId The ID of the attribute to filter by
     * @return ResponseEntity containing a list of AttributeValueDTO objects
     * @throws IllegalArgumentException if no attribute with the given ID exists
     */
    @GetMapping("/attribute/{attributeId}")
    public ResponseEntity<ApiResponse<List<AttributeValueDTO>>> getAttributeValuesByAttributeId(
            @PathVariable Long attributeId) {
        List<AttributeValueDTO> attributeValues = attributeValueService.getAttributeValuesByAttributeId(attributeId);
        return ResponseEntity.ok(ApiResponse.success(attributeValues));
    }

    /**
     * Get attribute values by base entity level ID.
     * Retrieves all attribute values associated with a specific base entity level.
     *
     * @param baseEntityLevelId The ID of the base entity level to filter by
     * @return ResponseEntity containing a list of AttributeValueDTO objects
     * @throws IllegalArgumentException if no base entity level with the given ID exists
     */
    @GetMapping("/base-entity-level/{baseEntityLevelId}")
    public ResponseEntity<ApiResponse<List<AttributeValueDTO>>> getAttributeValuesByBaseEntityLevelId(
            @PathVariable Long baseEntityLevelId) {
        List<AttributeValueDTO> attributeValues = attributeValueService.getAttributeValuesByBaseEntityLevelId(baseEntityLevelId);
        return ResponseEntity.ok(ApiResponse.success(attributeValues));
    }

    /**
     * Delete an attribute value.
     * Deletes an attribute value identified by its unique ID. If the entity doesn't exist,
     * the operation still returns a successful response.
     *
     * @param id The unique identifier of the attribute value to delete
     * @return ResponseEntity with no content, indicating successful deletion
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAttributeValue(
            @PathVariable Long id) {
        attributeValueService.deleteAttributeValue(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Attribute value deleted successfully"));
    }

    /**
     * Delete attribute values by attribute ID.
     * Deletes all attribute values associated with a specific attribute.
     *
     * @param attributeId The ID of the attribute whose values should be deleted
     * @return ResponseEntity with no content, indicating successful deletion
     * @throws IllegalArgumentException if no attribute with the given ID exists
     */
    @DeleteMapping("/attribute/{attributeId}")
    public ResponseEntity<ApiResponse<Void>> deleteAttributeValuesByAttributeId(
            @PathVariable Long attributeId) {
        attributeValueService.deleteAttributeValuesByAttributeId(attributeId);
        return ResponseEntity.ok(ApiResponse.success(null, "Attribute values deleted successfully"));
    }

    /**
     * Delete attribute values by base entity level ID.
     * Deletes all attribute values associated with a specific base entity level.
     *
     * @param baseEntityLevelId The ID of the base entity level whose values should be deleted
     * @return ResponseEntity with no content, indicating successful deletion
     * @throws IllegalArgumentException if no base entity level with the given ID exists
     */
    @DeleteMapping("/base-entity-level/{baseEntityLevelId}")
    public ResponseEntity<ApiResponse<Void>> deleteAttributeValuesByBaseEntityLevelId(
            @PathVariable Long baseEntityLevelId) {
        attributeValueService.deleteAttributeValuesByBaseEntityLevelId(baseEntityLevelId);
        return ResponseEntity.ok(ApiResponse.success(null, "Attribute values for base entity level deleted successfully"));
    }
}
