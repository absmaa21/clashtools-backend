package at.htlkaindorf.clashtoolsbackend.service;

import at.htlkaindorf.clashtoolsbackend.dto.attribute.AttributeRequestDTO;
import at.htlkaindorf.clashtoolsbackend.dto.attribute.AttributeResponseDTO;
import at.htlkaindorf.clashtoolsbackend.mapper.AttributeMapper;
import at.htlkaindorf.clashtoolsbackend.pojos.Attribute;
import at.htlkaindorf.clashtoolsbackend.pojos.AttributeName;
import at.htlkaindorf.clashtoolsbackend.repositories.AttributeNameRepository;
import at.htlkaindorf.clashtoolsbackend.repositories.AttributeRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for managing attributes in the system.
 * This service provides methods for creating, retrieving, updating, and deleting attributes,
 * which represent characteristics of base entities in the Clash Tools application.
 * It extends AbstractCrudService to inherit common CRUD operations.
 */
@Service
@Tag(name = "AttributeService", description = "Service for managing attributes")
public class AttributeService extends AbstractCrudService<Attribute, AttributeResponseDTO, AttributeRequestDTO, Long> {

    private final AttributeRepository attributeRepository;
    private final AttributeNameRepository attributeNameRepository;
    private final AttributeMapper attributeMapper;

    public AttributeService(AttributeRepository attributeRepository, AttributeNameRepository attributeNameRepository, AttributeMapper attributeMapper) {
        super(attributeRepository, attributeMapper);
        this.attributeRepository = attributeRepository;
        this.attributeNameRepository = attributeNameRepository;
        this.attributeMapper = attributeMapper;
    }

    @Override
    protected void setEntityId(Attribute entity, Long id) {
        entity.setId(id);
    }

    /**
     * Creates a new attribute in the database.
     * This method overrides the default implementation to handle the relationship with AttributeName.
     *
     * @param request The AttributeRequestDTO containing the data for the new attribute
     * @return An AttributeResponseDTO representing the newly created attribute
     * @throws IllegalArgumentException If the attribute name with the given ID does not exist
     */
    @Override
    public AttributeResponseDTO create(AttributeRequestDTO request) {
        AttributeName attributeName = attributeNameRepository.findById(request.getAttributeNameId())
                .orElseThrow(() -> new IllegalArgumentException("AttributeName not found"));

        Attribute attribute = attributeMapper.toEntity(request);
        attribute.setAttributeName(attributeName);

        Attribute savedAttribute = attributeRepository.save(attribute);
        return attributeMapper.toDTO(savedAttribute);
    }

    /**
     * Updates an existing attribute in the database.
     * This method overrides the default implementation to handle the relationship with AttributeName
     * and manage translations.
     *
     * @param id The unique identifier of the attribute to update
     * @param request The AttributeRequestDTO containing the updated data
     * @return An AttributeResponseDTO representing the updated attribute
     * @throws IllegalArgumentException If no attribute with the given ID exists in the database
     * @throws IllegalArgumentException If the attribute name with the given ID does not exist
     */
    @Override
    public AttributeResponseDTO update(Long id, AttributeRequestDTO request) {
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
