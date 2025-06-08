package at.htlkaindorf.clashtoolsbackend.service;

import at.htlkaindorf.clashtoolsbackend.dto.attribute.AttributeNameDTO;
import at.htlkaindorf.clashtoolsbackend.mapper.AttributeNameMapper;
import at.htlkaindorf.clashtoolsbackend.pojos.AttributeName;
import at.htlkaindorf.clashtoolsbackend.repositories.AttributeNameRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Service;

/**
 * Service for managing attribute names in the system.
 * This service provides methods for creating, retrieving, updating, and deleting attribute names,
 * which represent the types of attributes that can be assigned to base entities in the Clash Tools application.
 * It extends AbstractCrudService to inherit common CRUD operations.
 */
@Service
@Tag(name = "AttributeNameService", description = "Service for managing attribute names")
public class AttributeNameService extends AbstractCrudService<AttributeName, AttributeNameDTO, AttributeNameDTO, Long> {

    private final AttributeNameRepository attributeNameRepository;
    private final AttributeNameMapper attributeNameMapper;

    public AttributeNameService(AttributeNameRepository attributeNameRepository, AttributeNameMapper attributeNameMapper) {
        super(attributeNameRepository, attributeNameMapper);
        this.attributeNameRepository = attributeNameRepository;
        this.attributeNameMapper = attributeNameMapper;
    }

    @Override
    protected void setEntityId(AttributeName entity, Long id) {
        entity.setId(id);
    }

    /**
     * Creates a new attribute name in the database.
     * This method overrides the default implementation to add validation.
     *
     * @param request The AttributeNameDTO containing the data for the new attribute name
     * @return An AttributeNameDTO representing the newly created attribute name
     * @throws IllegalArgumentException If an attribute name with the same name already exists
     */
    @Override
    public AttributeNameDTO create(AttributeNameDTO request) {
        if (attributeNameRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Attribute name already exists");
        }

        return super.create(request);
    }

    /**
     * Updates an existing attribute name in the database.
     * This method overrides the default implementation to add validation.
     *
     * @param id The unique identifier of the attribute name to update
     * @param request The AttributeNameDTO containing the updated data
     * @return An AttributeNameDTO representing the updated attribute name
     * @throws IllegalArgumentException If no attribute name with the given ID exists in the database
     * @throws IllegalArgumentException If an attribute name with the same name already exists (and it's not the one being updated)
     */
    @Override
    public AttributeNameDTO update(Long id, AttributeNameDTO request) {
        AttributeName attributeName = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Attribute name not found"));

        if (!attributeName.getName().equals(request.getName()) &&
                attributeNameRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Attribute name already exists");
        }

        return super.update(id, request);
    }

    /**
     * Retrieves a specific attribute name by its name.
     * This method searches for an attribute name with the given name in the database,
     * throws an exception if not found, and converts it to a DTO for the presentation layer.
     *
     * @param name The name of the attribute name to retrieve
     * @return An AttributeNameDTO representing the requested attribute name
     * @throws IllegalArgumentException If no attribute name with the given name exists in the database
     */
    public AttributeNameDTO getAttributeNameByName(String name) {
        AttributeName attributeName = attributeNameRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Attribute name not found"));
        return attributeNameMapper.toDTO(attributeName);
    }
}
