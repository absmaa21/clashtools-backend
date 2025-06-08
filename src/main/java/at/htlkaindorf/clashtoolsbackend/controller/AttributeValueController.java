package at.htlkaindorf.clashtoolsbackend.controller;

import at.htlkaindorf.clashtoolsbackend.dto.ApiResponse;
import at.htlkaindorf.clashtoolsbackend.dto.attribute.AttributeValueDTO;
import at.htlkaindorf.clashtoolsbackend.service.AttributeValueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/attribute-values")
@RequiredArgsConstructor
@Tag(name = "Attribute Values", description = "API for managing attribute values")
public class AttributeValueController {

    private final AttributeValueService attributeValueService;

    @GetMapping
    @Operation(summary = "Get all attribute values",
               description = "Retrieves a list of all attribute values in the system")
    public ResponseEntity<ApiResponse<List<AttributeValueDTO>>> getAllAttributeValues() {
        log.debug("Fetching all attribute values");
        List<AttributeValueDTO> attributeValues = attributeValueService.getAllAttributeValues();
        return ResponseEntity.ok(ApiResponse.success(attributeValues));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get attribute value by ID",
               description = "Retrieves a specific attribute value identified by its unique ID")
    public ResponseEntity<ApiResponse<AttributeValueDTO>> getAttributeValueById(
            @PathVariable Long id) {
        log.debug("Fetching attribute value with ID: {}", id);
        AttributeValueDTO attributeValue = attributeValueService.getAttributeValueById(id);
        return ResponseEntity.ok(ApiResponse.success(attributeValue));
    }

    @GetMapping("/attribute/{attributeId}")
    @Operation(summary = "Get attribute values by attribute ID",
               description = "Retrieves all attribute values associated with a specific attribute")
    public ResponseEntity<ApiResponse<List<AttributeValueDTO>>> getAttributeValuesByAttributeId(
            @PathVariable Long attributeId) {
        log.debug("Fetching attribute values for attribute ID: {}", attributeId);
        List<AttributeValueDTO> attributeValues = attributeValueService.getAttributeValuesByAttributeId(attributeId);
        return ResponseEntity.ok(ApiResponse.success(attributeValues));
    }

    @GetMapping("/base-entity-level/{baseEntityLevelId}")
    @Operation(summary = "Get attribute values by base entity level ID",
               description = "Retrieves all attribute values associated with a specific base entity level")
    public ResponseEntity<ApiResponse<List<AttributeValueDTO>>> getAttributeValuesByBaseEntityLevelId(
            @PathVariable Long baseEntityLevelId) {
        log.debug("Fetching attribute values for base entity level ID: {}", baseEntityLevelId);
        List<AttributeValueDTO> attributeValues = attributeValueService.getAttributeValuesByBaseEntityLevelId(baseEntityLevelId);
        return ResponseEntity.ok(ApiResponse.success(attributeValues));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an attribute value",
               description = "Deletes an attribute value identified by its unique ID")
    public ResponseEntity<ApiResponse<Void>> deleteAttributeValue(
            @PathVariable Long id) {
        log.debug("Deleting attribute value with ID: {}", id);
        attributeValueService.deleteAttributeValue(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Attribute value deleted successfully"));
    }

    @DeleteMapping("/attribute/{attributeId}")
    @Operation(summary = "Delete attribute values by attribute ID",
               description = "Deletes all attribute values associated with a specific attribute")
    public ResponseEntity<ApiResponse<Void>> deleteAttributeValuesByAttributeId(
            @PathVariable Long attributeId) {
        log.debug("Deleting attribute values for attribute ID: {}", attributeId);
        attributeValueService.deleteAttributeValuesByAttributeId(attributeId);
        return ResponseEntity.ok(ApiResponse.success(null, "Attribute values deleted successfully"));
    }

    @DeleteMapping("/base-entity-level/{baseEntityLevelId}")
    @Operation(summary = "Delete attribute values by base entity level ID",
               description = "Deletes all attribute values associated with a specific base entity level")
    public ResponseEntity<ApiResponse<Void>> deleteAttributeValuesByBaseEntityLevelId(
            @PathVariable Long baseEntityLevelId) {
        log.debug("Deleting attribute values for base entity level ID: {}", baseEntityLevelId);
        attributeValueService.deleteAttributeValuesByBaseEntityLevelId(baseEntityLevelId);
        return ResponseEntity.ok(ApiResponse.success(null, "Attribute values for base entity level deleted successfully"));
    }
}
