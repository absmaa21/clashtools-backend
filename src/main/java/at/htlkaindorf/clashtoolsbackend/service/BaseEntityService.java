package at.htlkaindorf.clashtoolsbackend.service;

import at.htlkaindorf.clashtoolsbackend.dto.baseentity.BaseEntityDTO;
import at.htlkaindorf.clashtoolsbackend.dto.baseentity.BaseEntityRequestDTO;
import at.htlkaindorf.clashtoolsbackend.mapper.BaseEntityMapper;
import at.htlkaindorf.clashtoolsbackend.pojos.BaseEntity;
import at.htlkaindorf.clashtoolsbackend.pojos.Category;
import at.htlkaindorf.clashtoolsbackend.repositories.BaseEntityRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
     * Results are cached to improve performance for this frequently accessed data.
     *
     * @return A list of BaseEntityDTO objects representing all base entities in the system
     */
    @Cacheable(value = "baseEntities", unless = "#result == null || #result.isEmpty()")
    public List<BaseEntityDTO> getAllBaseEntities() {
        List<BaseEntity> baseEntities = baseEntityRepository.findAll();
        return baseEntityMapper.toDTOList(baseEntities);
    }

    /**
     * Retrieves a specific base entity by its unique identifier.
     * This method searches for a base entity with the given ID in the database,
     * throws an exception if not found, and converts it to a DTO for the presentation layer.
     * Results are cached to improve performance for this frequently accessed data.
     *
     * @param id The unique identifier of the base entity to retrieve
     * @return A BaseEntityDTO representing the requested base entity
     * @throws IllegalArgumentException If no base entity with the given ID exists in the database
     */
    @Cacheable(value = "baseEntity", key = "#id", unless = "#result == null")
    public BaseEntityDTO getBaseEntityById(Long id) {
        BaseEntity baseEntity = baseEntityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("BaseEntity not found"));
        return baseEntityMapper.toDTO(baseEntity);
    }

    /**
     * Creates a new base entity in the database.
     * This method converts the provided request DTO to an entity object,
     * persists it to the database, and returns the created entity as a DTO.
     * This method also updates the cache for the individual entity and evicts the cache for all entities.
     *
     * @param request The BaseEntityRequestDTO containing the data for the new base entity
     * @return A BaseEntityDTO representing the newly created base entity
     */
    @Caching(
        put = {
            @CachePut(value = "baseEntity", key = "#result.id")
        },
        evict = {
            @CacheEvict(value = "baseEntities", allEntries = true)
        }
    )
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
     * This method also updates the cache for the individual entity and evicts the cache for all entities.
     *
     * @param id The unique identifier of the base entity to update
     * @param request The BaseEntityRequestDTO containing the updated data
     * @return A BaseEntityDTO representing the updated base entity
     * @throws IllegalArgumentException If no base entity with the given ID exists in the database
     */
    @Caching(
        put = {
            @CachePut(value = "baseEntity", key = "#id")
        },
        evict = {
            @CacheEvict(value = "baseEntities", allEntries = true)
        }
    )
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
     * This method also evicts the cache for the individual entity and all entities.
     *
     * @param id The unique identifier of the base entity to delete
     */
    @Caching(
        evict = {
            @CacheEvict(value = "baseEntity", key = "#id"),
            @CacheEvict(value = "baseEntities", allEntries = true)
        }
    )
    public void deleteBaseEntity(Long id) {
        baseEntityRepository.deleteById(id);
    }

    /**
     * Retrieves all base entities with the specified category.
     * This method uses an optimized query that leverages the index on the category field.
     * Results are cached to improve performance for this frequently accessed data.
     *
     * @param category The category to filter by
     * @return A list of BaseEntityDTO objects representing base entities with the specified category
     */
    @Cacheable(value = "baseEntitiesByCategory", key = "#category", unless = "#result == null || #result.isEmpty()")
    public List<BaseEntityDTO> getBaseEntitiesByCategory(Category category) {
        List<BaseEntity> baseEntities = baseEntityRepository.findByCategory(category);
        return baseEntityMapper.toDTOList(baseEntities);
    }

    /**
     * Retrieves all base entities with names containing the specified string (case-insensitive).
     * This method uses an optimized query for case-insensitive name searching.
     * Results are cached to improve performance for this frequently accessed data.
     *
     * @param name The name substring to search for
     * @return A list of BaseEntityDTO objects representing base entities with matching names
     */
    @Cacheable(value = "baseEntitiesByName", key = "#name", unless = "#result == null || #result.isEmpty()")
    public List<BaseEntityDTO> getBaseEntitiesByNameContaining(String name) {
        List<BaseEntity> baseEntities = baseEntityRepository.findByNameContainingIgnoreCase(name);
        return baseEntityMapper.toDTOList(baseEntities);
    }
}
