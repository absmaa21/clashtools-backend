package at.htlkaindorf.clashtoolsbackend.service;

import at.htlkaindorf.clashtoolsbackend.dto.attribute.AttributeRequestDTO;
import at.htlkaindorf.clashtoolsbackend.dto.attribute.AttributeResponseDTO;
import at.htlkaindorf.clashtoolsbackend.mapper.AttributeMapper;
import at.htlkaindorf.clashtoolsbackend.pojos.Attribute;
import at.htlkaindorf.clashtoolsbackend.pojos.AttributeName;
import at.htlkaindorf.clashtoolsbackend.repositories.AttributeNameRepository;
import at.htlkaindorf.clashtoolsbackend.repositories.AttributeRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for managing attributes in the system.
 * This service provides methods for creating, retrieving, updating, and deleting attributes,
 * which represent characteristics of base entities in the Clash Tools application.
 * It acts as an intermediary between the controller layer and the repository layer,
 * handling business logic and data transformation.
 */
@Service
@RequiredArgsConstructor
@Tag(name = "AttributeService", description = "Service for managing attributes")
public class AttributeService {

    private final AttributeRepository attributeRepository;
    private final AttributeNameRepository attributeNameRepository;
    private final AttributeMapper attributeMapper;

    /**
     * Retrieves all attributes from the database.
     * This method fetches all attributes stored in the system and converts them to DTOs
     * for use in the presentation layer.
     *
     * @return A list of AttributeResponseDTO objects representing all attributes in the system
     */
    public List<AttributeResponseDTO> getAllAttributes() {
        List<Attribute> attributes = attributeRepository.findAll();
        return attributeMapper.toDTOList(attributes);
    }

    /**
     * Retrieves a specific attribute by its unique identifier.
     * This method searches for an attribute with the given ID in the database,
     * throws an exception if not found, and converts it to a DTO for the presentation layer.
     *
     * @param id The unique identifier of the attribute to retrieve
     * @return An AttributeResponseDTO representing the requested attribute
     * @throws IllegalArgumentException If no attribute with the given ID exists in the database
     */
    public AttributeResponseDTO getAttributeById(Long id) {
        Attribute attribute = attributeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Attribute not found"));
        return attributeMapper.toDTO(attribute);
    }

    /**
     * Creates a new attribute in the database.
     * This method converts the provided request DTO to an entity object,
     * persists it to the database, and returns the created entity as a DTO.
     *
     * @param request The AttributeRequestDTO containing the data for the new attribute
     * @return An AttributeResponseDTO representing the newly created attribute
     * @throws IllegalArgumentException If the attribute name with the given ID does not exist
     */
    public AttributeResponseDTO createAttribute(AttributeRequestDTO request) {
        AttributeName attributeName = attributeNameRepository.findById(request.getAttributeNameId())
                .orElseThrow(() -> new IllegalArgumentException("AttributeName not found"));

        Attribute attribute = attributeMapper.toEntity(request);
        attribute.setAttributeName(attributeName);

        Attribute savedAttribute = attributeRepository.save(attribute);
        return attributeMapper.toDTO(savedAttribute);
    }

    /**
     * Updates an existing attribute in the database.
     * This method retrieves the attribute with the given ID, updates its properties
     * with the values from the request DTO, persists the changes to the database,
     * and returns the updated entity as a DTO.
     *
     * @param id The unique identifier of the attribute to update
     * @param request The AttributeRequestDTO containing the updated data
     * @return An AttributeResponseDTO representing the updated attribute
     * @throws IllegalArgumentException If no attribute with the given ID exists in the database
     * @throws IllegalArgumentException If the attribute name with the given ID does not exist
     */
    public AttributeResponseDTO updateAttribute(Long id, AttributeRequestDTO request) {
        Attribute attribute = attributeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Attribute not found"));

        AttributeName attributeName = attributeNameRepository.findById(request.getAttributeNameId())
                .orElseThrow(() -> new IllegalArgumentException("AttributeName not found"));

        attribute.setAttributeName(attributeName);

        // Update translations if provided
        if (request.getTranslations() != null && !request.getTranslations().isEmpty()) {
            // Clear existing translations and add new ones
            attribute.getTranslations().clear();
            Attribute updatedAttribute = attributeMapper.toEntity(request);
            if (updatedAttribute.getTranslations() != null) {
                updatedAttribute.getTranslations().forEach(translation -> {
                    translation.setAttribute(attribute);
                    attribute.getTranslations().add(translation);
                });
            }
        }

        Attribute updatedAttribute = attributeRepository.save(attribute);
        return attributeMapper.toDTO(updatedAttribute);
    }

    /**
     * Deletes an attribute from the database.
     * This method removes the attribute with the given ID from the database.
     * If no entity with the given ID exists, the operation completes silently.
     *
     * @param id The unique identifier of the attribute to delete
     */
    public void deleteAttribute(Long id) {
        attributeRepository.deleteById(id);
    }

    /**
     * Retrieves all attributes associated with a specific attribute name.
     * This method fetches all attributes with the given attribute name ID and converts them to DTOs.
     *
     * @param attributeNameId The ID of the attribute name to filter by
     * @return A list of AttributeResponseDTO objects representing attributes with the specified attribute name
     */
    public List<AttributeResponseDTO> getAttributesByAttributeNameId(Long attributeNameId) {
        List<Attribute> attributes = attributeRepository.findByAttributeNameId(attributeNameId);
        return attributeMapper.toDTOList(attributes);
    }
}
