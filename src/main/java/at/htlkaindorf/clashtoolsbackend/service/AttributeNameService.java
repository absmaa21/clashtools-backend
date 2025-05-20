package at.htlkaindorf.clashtoolsbackend.service;

import at.htlkaindorf.clashtoolsbackend.dto.attribute.AttributeNameDTO;
import at.htlkaindorf.clashtoolsbackend.mapper.AttributeNameMapper;
import at.htlkaindorf.clashtoolsbackend.pojos.AttributeName;
import at.htlkaindorf.clashtoolsbackend.repositories.AttributeNameRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for managing attribute names in the system.
 * This service provides methods for creating, retrieving, updating, and deleting attribute names,
 * which represent the types of attributes that can be assigned to base entities in the Clash Tools application.
 * It acts as an intermediary between the controller layer and the repository layer,
 * handling business logic and data transformation.
 */
@Service
@RequiredArgsConstructor
@Tag(name = "AttributeNameService", description = "Service for managing attribute names")
public class AttributeNameService {

    private final AttributeNameRepository attributeNameRepository;
    private final AttributeNameMapper attributeNameMapper;

    /**
     * Retrieves all attribute names from the database.
     * This method fetches all attribute names stored in the system and converts them to DTOs
     * for use in the presentation layer.
     *
     * @return A list of AttributeNameDTO objects representing all attribute names in the system
     */
    public List<AttributeNameDTO> getAllAttributeNames() {
        List<AttributeName> attributeNames = attributeNameRepository.findAll();
        return attributeNameMapper.toDTOList(attributeNames);
    }

    /**
     * Retrieves a specific attribute name by its unique identifier.
     * This method searches for an attribute name with the given ID in the database,
     * throws an exception if not found, and converts it to a DTO for the presentation layer.
     *
     * @param id The unique identifier of the attribute name to retrieve
     * @return An AttributeNameDTO representing the requested attribute name
     * @throws IllegalArgumentException If no attribute name with the given ID exists in the database
     */
    public AttributeNameDTO getAttributeNameById(Long id) {
        AttributeName attributeName = attributeNameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Attribute name not found"));
        return attributeNameMapper.toDTO(attributeName);
    }

    /**
     * Creates a new attribute name in the database.
     * This method converts the provided DTO to an entity object,
     * persists it to the database, and returns the created entity as a DTO.
     *
     * @param attributeNameDTO The AttributeNameDTO containing the data for the new attribute name
     * @return An AttributeNameDTO representing the newly created attribute name
     * @throws IllegalArgumentException If an attribute name with the same name already exists
     */
    public AttributeNameDTO createAttributeName(AttributeNameDTO attributeNameDTO) {
        if (attributeNameRepository.existsByName(attributeNameDTO.getName())) {
            throw new IllegalArgumentException("Attribute name already exists");
        }

        AttributeName attributeName = attributeNameMapper.toEntity(attributeNameDTO);
        AttributeName savedAttributeName = attributeNameRepository.save(attributeName);
        return attributeNameMapper.toDTO(savedAttributeName);
    }

    /**
     * Updates an existing attribute name in the database.
     * This method retrieves the attribute name with the given ID, updates its properties
     * with the values from the DTO, persists the changes to the database,
     * and returns the updated entity as a DTO.
     *
     * @param id The unique identifier of the attribute name to update
     * @param attributeNameDTO The AttributeNameDTO containing the updated data
     * @return An AttributeNameDTO representing the updated attribute name
     * @throws IllegalArgumentException If no attribute name with the given ID exists in the database
     * @throws IllegalArgumentException If an attribute name with the same name already exists (and it's not the one being updated)
     */
    public AttributeNameDTO updateAttributeName(Long id, AttributeNameDTO attributeNameDTO) {
        AttributeName attributeName = attributeNameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Attribute name not found"));

        // Check if the name is being changed and if the new name already exists
        if (!attributeName.getName().equals(attributeNameDTO.getName()) &&
                attributeNameRepository.existsByName(attributeNameDTO.getName())) {
            throw new IllegalArgumentException("Attribute name already exists");
        }

        attributeName.setName(attributeNameDTO.getName());
        AttributeName updatedAttributeName = attributeNameRepository.save(attributeName);
        return attributeNameMapper.toDTO(updatedAttributeName);
    }

    /**
     * Deletes an attribute name from the database.
     * This method removes the attribute name with the given ID from the database.
     * If no entity with the given ID exists, the operation completes silently.
     *
     * @param id The unique identifier of the attribute name to delete
     */
    public void deleteAttributeName(Long id) {
        attributeNameRepository.deleteById(id);
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
