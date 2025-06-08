package at.htlkaindorf.clashtoolsbackend.service;

import at.htlkaindorf.clashtoolsbackend.dto.attribute.AttributeValueDTO;
import at.htlkaindorf.clashtoolsbackend.mapper.AttributeValueMapper;
import at.htlkaindorf.clashtoolsbackend.pojos.Attribute;
import at.htlkaindorf.clashtoolsbackend.pojos.AttributeValue;
import at.htlkaindorf.clashtoolsbackend.pojos.BaseEntityLevel;
import at.htlkaindorf.clashtoolsbackend.repositories.AttributeRepository;
import at.htlkaindorf.clashtoolsbackend.repositories.AttributeValueRepository;
import at.htlkaindorf.clashtoolsbackend.repositories.BaseEntityLevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for managing attribute values.
 * This service handles operations related to attribute values such as creation, updates, and queries.
 * It acts as an intermediary between the controller layer and the repository layer,
 * handling business logic and data transformation.
 */
@Service
@RequiredArgsConstructor
public class AttributeValueService {

    private final AttributeValueRepository attributeValueRepository;
    private final AttributeRepository attributeRepository;
    private final BaseEntityLevelRepository baseEntityLevelRepository;
    private final AttributeValueMapper attributeValueMapper;

    /**
     * Retrieves all attribute values from the database.
     * This method fetches all attribute values stored in the system and converts them to DTOs
     * for use in the presentation layer.
     *
     * @return A list of AttributeValueDTO objects representing all attribute values in the system
     */
    public List<AttributeValueDTO> getAllAttributeValues() {
        List<AttributeValue<?>> attributeValues = attributeValueRepository.findAll();
        return attributeValueMapper.toDTOList(attributeValues);
    }

    /**
     * Retrieves a specific attribute value by its unique identifier.
     * This method searches for an attribute value with the given ID in the database,
     * throws an exception if not found, and converts it to a DTO for the presentation layer.
     *
     * @param id The unique identifier of the attribute value to retrieve
     * @return An AttributeValueDTO representing the requested attribute value
     * @throws IllegalArgumentException If no attribute value with the given ID exists in the database
     */
    public AttributeValueDTO getAttributeValueById(Long id) {
        AttributeValue<?> attributeValue = attributeValueRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Attribute value not found"));
        return attributeValueMapper.toDTO(attributeValue);
    }

    /**
     * Retrieves all attribute values associated with a specific attribute.
     * This method fetches all attribute values with the given attribute ID and converts them to DTOs.
     *
     * @param attributeId The ID of the attribute to filter by
     * @return A list of AttributeValueDTO objects representing attribute values with the specified attribute
     * @throws IllegalArgumentException If no attribute with the given ID exists in the database
     */
    public List<AttributeValueDTO> getAttributeValuesByAttributeId(Long attributeId) {
        attributeRepository.findById(attributeId)
                .orElseThrow(() -> new IllegalArgumentException("Attribute not found"));

        List<AttributeValue<?>> attributeValues = attributeValueRepository.findByAttributeId(attributeId);
        return attributeValueMapper.toDTOList(attributeValues);
    }

    /**
     * Retrieves all attribute values associated with a specific base entity level.
     * This method fetches all attribute values with the given base entity level ID and converts them to DTOs.
     *
     * @param baseEntityLevelId The ID of the base entity level to filter by
     * @return A list of AttributeValueDTO objects representing attribute values with the specified base entity level
     * @throws IllegalArgumentException If no base entity level with the given ID exists in the database
     */
    public List<AttributeValueDTO> getAttributeValuesByBaseEntityLevelId(Long baseEntityLevelId) {
        baseEntityLevelRepository.findById(baseEntityLevelId)
                .orElseThrow(() -> new IllegalArgumentException("Base entity level not found"));

        List<AttributeValue<?>> attributeValues = attributeValueRepository.findByBaseEntityLevelId(baseEntityLevelId);
        return attributeValueMapper.toDTOList(attributeValues);
    }

    /**
     * Deletes an attribute value from the database.
     * This method removes the attribute value with the given ID from the database.
     * If no entity with the given ID exists, the operation completes silently.
     *
     * @param id The unique identifier of the attribute value to delete
     */
    public void deleteAttributeValue(Long id) {
        attributeValueRepository.deleteById(id);
    }

    /**
     * Deletes all attribute values associated with a specific attribute.
     * This method removes all attribute values with the given attribute ID from the database.
     *
     * @param attributeId The ID of the attribute whose values should be deleted
     * @throws IllegalArgumentException If no attribute with the given ID exists in the database
     */
    public void deleteAttributeValuesByAttributeId(Long attributeId) {
        Attribute attribute = attributeRepository.findById(attributeId)
                .orElseThrow(() -> new IllegalArgumentException("Attribute not found"));
        attributeValueRepository.deleteByAttribute(attribute);
    }

    /**
     * Deletes all attribute values associated with a specific base entity level.
     * This method removes all attribute values with the given base entity level ID from the database.
     *
     * @param baseEntityLevelId The ID of the base entity level whose values should be deleted
     * @throws IllegalArgumentException If no base entity level with the given ID exists in the database
     */
    public void deleteAttributeValuesByBaseEntityLevelId(Long baseEntityLevelId) {
        BaseEntityLevel baseEntityLevel = baseEntityLevelRepository.findById(baseEntityLevelId)
                .orElseThrow(() -> new IllegalArgumentException("Base entity level not found"));
        attributeValueRepository.deleteByBaseEntityLevel(baseEntityLevel);
    }
}
