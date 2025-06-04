package at.htlkaindorf.clashtoolsbackend.service;

import at.htlkaindorf.clashtoolsbackend.dto.baseentity.BaseEntityDTO;
import at.htlkaindorf.clashtoolsbackend.dto.baseentity.BaseEntityRequestDTO;
import at.htlkaindorf.clashtoolsbackend.dto.baseentity.BaseEntityResponseDTO;
import at.htlkaindorf.clashtoolsbackend.mapper.BaseEntityMapper;
import at.htlkaindorf.clashtoolsbackend.pojos.BaseEntity;
import at.htlkaindorf.clashtoolsbackend.pojos.Category;
import at.htlkaindorf.clashtoolsbackend.repositories.BaseEntityRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for managing base entities in the system.
 * This service provides methods for creating, retrieving, updating, and deleting base entities,
 * which are fundamental game elements in the Clash Tools application.
 * It acts as an intermediary between the controller layer and the repository layer,
 * handling business logic and data transformation.
 */
@Service
@RequiredArgsConstructor
@Tag(name = "BaseEntityService", description = "Service for managing base entities")
public class BaseEntityService {

    private final BaseEntityRepository baseEntityRepository;
    private final BaseEntityMapper baseEntityMapper;

    /**
     * Retrieves all base entities from the database.
     * This method fetches all base entities stored in the system and converts them to DTOs
     * for use in the presentation layer.
     *
     * @return A list of BaseEntityDTO objects representing all base entities in the system
     */
    public List<BaseEntityDTO> getAllBaseEntities() {
        List<BaseEntity> baseEntities = baseEntityRepository.findAll();
        return baseEntityMapper.toDTOList(baseEntities);
    }

    /**
     * Retrieves a specific base entity by its unique identifier.
     * This method searches for a base entity with the given ID in the database,
     * throws an exception if not found, and converts it to a DTO for the presentation layer.
     *
     * @param id The unique identifier of the base entity to retrieve
     * @return A BaseEntityDTO representing the requested base entity
     * @throws IllegalArgumentException If no base entity with the given ID exists in the database
     */
    public BaseEntityDTO getBaseEntityById(Long id) {
        BaseEntity baseEntity = baseEntityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("BaseEntity not found"));
        return baseEntityMapper.toDTO(baseEntity);
    }

    /**
     * Creates a new base entity in the database.
     * This method converts the provided request DTO to an entity object,
     * persists it to the database, and returns the created entity as a DTO.
     *
     * @param request The BaseEntityRequestDTO containing the data for the new base entity
     * @return A BaseEntityDTO representing the newly created base entity
     */
    public BaseEntityDTO createBaseEntity(BaseEntityRequestDTO request) {
        BaseEntity baseEntity = baseEntityMapper.toEntity(request);
        BaseEntity savedBaseEntity = baseEntityRepository.save(baseEntity);
        return baseEntityMapper.toDTO(savedBaseEntity);
    }

    /**
     * Updates an existing base entity in the database.
     * This method retrieves the base entity with the given ID, updates its properties
     * with the values from the request DTO, persists the changes to the database,
     * and returns the updated entity as a DTO.
     *
     * @param id The unique identifier of the base entity to update
     * @param request The BaseEntityRequestDTO containing the updated data
     * @return A BaseEntityDTO representing the updated base entity
     * @throws IllegalArgumentException If no base entity with the given ID exists in the database
     */
    public BaseEntityDTO updateBaseEntity(Long id, BaseEntityRequestDTO request) {
        BaseEntity baseEntity = baseEntityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("BaseEntity not found"));

        baseEntity.setName(request.getName());
        BaseEntity updatedBaseEntity = baseEntityRepository.save(baseEntity);
        return baseEntityMapper.toDTO(updatedBaseEntity);
    }

    /**
     * Deletes a base entity from the database.
     * This method removes the base entity with the given ID from the database.
     * If no entity with the given ID exists, the operation completes silently.
     *
     * @param id The unique identifier of the base entity to delete
     */
    public void deleteBaseEntity(Long id) {
        baseEntityRepository.deleteById(id);
    }

    /**
     * Retrieves all base entities with the specified category.
     * This method uses an optimized query that leverages the index on the category field.
     *
     * @param category The category to filter by
     * @return A list of BaseEntityDTO objects representing base entities with the specified category
     */
    public List<BaseEntityDTO> getBaseEntitiesByCategory(Category category) {
        List<BaseEntity> baseEntities = baseEntityRepository.findByCategory(category);
        return baseEntityMapper.toDTOList(baseEntities);
    }

    /**
     * Retrieves all base entities with names containing the specified string (case-insensitive).
     * This method uses an optimized query for case-insensitive name searching.
     *
     * @param name The name substring to search for
     * @return A list of BaseEntityDTO objects representing base entities with matching names
     */
    public List<BaseEntityDTO> getBaseEntitiesByNameContaining(String name) {
        List<BaseEntity> baseEntities = baseEntityRepository.findByNameContainingIgnoreCase(name);
        return baseEntityMapper.toDTOList(baseEntities);
    }

    /**
     * Retrieves all base entities from the database with their baseEntityLevels included.
     * This method fetches all base entities stored in the system and converts them to ResponseDTOs
     * that include the baseEntityLevels for use in the presentation layer.
     *
     * @return A list of BaseEntityResponseDTO objects representing all base entities in the system with their levels
     */
    public List<BaseEntityResponseDTO> getAllBaseEntitiesWithLevels() {
        List<BaseEntity> baseEntities = baseEntityRepository.findAll();
        return baseEntityMapper.toResponseDTOList(baseEntities);
    }

    /**
     * Retrieves a specific base entity by its unique identifier with its baseEntityLevels included.
     * This method searches for a base entity with the given ID in the database,
     * throws an exception if not found, and converts it to a ResponseDTO that includes the baseEntityLevels.
     *
     * @param id The unique identifier of the base entity to retrieve
     * @return A BaseEntityResponseDTO representing the requested base entity with its levels
     * @throws IllegalArgumentException If no base entity with the given ID exists in the database
     */
    public BaseEntityResponseDTO getBaseEntityByIdWithLevels(Long id) {
        BaseEntity baseEntity = baseEntityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("BaseEntity not found"));
        return baseEntityMapper.toResponseDTO(baseEntity);
    }
}
