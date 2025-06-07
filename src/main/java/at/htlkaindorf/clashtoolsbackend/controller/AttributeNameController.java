package at.htlkaindorf.clashtoolsbackend.controller;

import at.htlkaindorf.clashtoolsbackend.dto.ApiResponse;
import at.htlkaindorf.clashtoolsbackend.dto.attribute.AttributeNameDTO;
import at.htlkaindorf.clashtoolsbackend.pojos.AttributeName;
import at.htlkaindorf.clashtoolsbackend.service.AttributeNameService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Attribute Name Controller.
 * Provides REST API endpoints for managing attribute names in the system.
 * This controller extends CrudController to inherit common CRUD operations.
 */
@RestController
@RequestMapping("/api/attribute-names")
@Tag(name = "Attribute Names", description = "API for managing attribute names")
public class AttributeNameController extends CrudController<AttributeName, AttributeNameDTO, AttributeNameDTO, Long> {

    private final AttributeNameService attributeNameService;

    public AttributeNameController(AttributeNameService attributeNameService) {
        super(attributeNameService);
        this.attributeNameService = attributeNameService;
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

}
