package at.htlkaindorf.clashtoolsbackend.controller;

import at.htlkaindorf.clashtoolsbackend.dto.ApiResponse;
import at.htlkaindorf.clashtoolsbackend.dto.attribute.AttributeRequestDTO;
import at.htlkaindorf.clashtoolsbackend.dto.attribute.AttributeResponseDTO;
import at.htlkaindorf.clashtoolsbackend.pojos.Attribute;
import at.htlkaindorf.clashtoolsbackend.service.AttributeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/attributes")
@Tag(name = "Attributes", description = "API for managing attributes")
public class AttributeController extends CrudController<Attribute, AttributeResponseDTO, AttributeRequestDTO, Long> {

    private final AttributeService attributeService;

    public AttributeController(AttributeService attributeService) {
        super(attributeService);
        this.attributeService = attributeService;
    }


    @GetMapping("/by-attribute-name/{attributeNameId}")
    @Operation(summary = "Get attributes by attribute name ID",
               description = "Retrieves all attributes associated with a specific attribute name")
    public ResponseEntity<ApiResponse<List<AttributeResponseDTO>>> getAttributesByAttributeNameId(
            @PathVariable Long attributeNameId) {
        log.debug("Fetching attributes for attribute name ID: {}", attributeNameId);
        List<AttributeResponseDTO> attributes = attributeService.getAttributesByAttributeNameId(attributeNameId);
        return ResponseEntity.ok(ApiResponse.success(attributes));
    }

}
