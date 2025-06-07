package at.htlkaindorf.clashtoolsbackend.service;

import at.htlkaindorf.clashtoolsbackend.dto.baseentity.BaseEntityDTO;
import at.htlkaindorf.clashtoolsbackend.dto.baseentity.BaseEntityRequestDTO;
import at.htlkaindorf.clashtoolsbackend.dto.baseentity.BaseEntityResponseDTO;
import at.htlkaindorf.clashtoolsbackend.mapper.BaseEntityMapper;
import at.htlkaindorf.clashtoolsbackend.pojos.BaseEntity;
import at.htlkaindorf.clashtoolsbackend.pojos.Category;
import at.htlkaindorf.clashtoolsbackend.repositories.BaseEntityRepository;
import at.htlkaindorf.clashtoolsbackend.repositories.projections.BaseEntitySummary;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service for managing base entities in the system.
 * This service provides methods for creating, retrieving, updating, and deleting base entities,
 * which are fundamental game elements in the Clash Tools application.
 * It extends the AbstractCrudService to inherit common CRUD operations.
 */
@Service
@Tag(name = "BaseEntityService", description = "Service for managing base entities")
public class BaseEntityService extends AbstractCrudService<BaseEntity, BaseEntityDTO, BaseEntityRequestDTO, Long> {

    private final BaseEntityRepository baseEntityRepository;
    private final BaseEntityMapper baseEntityMapper;

    /**
     * Constructor for dependency injection.
     *
     * @param baseEntityRepository The repository for BaseEntity operations
     * @param baseEntityMapper The mapper for converting between BaseEntity and DTOs
     */
    public BaseEntityService(BaseEntityRepository baseEntityRepository, BaseEntityMapper baseEntityMapper) {
        super(baseEntityRepository, baseEntityMapper);
        this.baseEntityRepository = baseEntityRepository;
        this.baseEntityMapper = baseEntityMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setEntityId(BaseEntity entity, Long id) {
        entity.setId(id);
    }


    /**
     * Retrieves all base entities with the specified category.
     * This method uses an optimized query that leverages the index on the category field.
     *
     * @param category The category to filter by
     * @return A list of BaseEntityDTO objects representing base entities with the specified category
     */
    public List<BaseEntityDTO> getBaseEntitiesByCategory(Category category) {
        return baseEntityMapper.toDTOList(baseEntityRepository.findByCategory(category));
    }

    /**
     * Retrieves all base entities with names containing the specified string (case-insensitive).
     * This method uses an optimized query for case-insensitive name searching.
     *
     * @param name The name substring to search for
     * @return A list of BaseEntityDTO objects representing base entities with matching names
     */
    public List<BaseEntityDTO> getBaseEntitiesByNameContaining(String name) {
        return baseEntityMapper.toDTOList(baseEntityRepository.findByNameContainingIgnoreCase(name));
    }

    /**
     * Retrieves all base entities from the database with their baseEntityLevels included.
     * This method fetches all base entities stored in the system and converts them to ResponseDTOs
     * that include the baseEntityLevels for use in the presentation layer.
     * It uses a custom query that eagerly loads the baseEntityLevels to avoid lazy loading issues.
     *
     * @return A list of BaseEntityResponseDTO objects representing all base entities in the system with their levels
     */
    public List<BaseEntityResponseDTO> getAllBaseEntitiesWithLevels() {
        return baseEntityMapper.toResponseDTOList(baseEntityRepository.findAllWithLevels());
    }

    /**
     * Retrieves a specific base entity by its unique identifier with its baseEntityLevels included.
     * This method searches for a base entity with the given ID in the database,
     * throws an exception if not found, and converts it to a ResponseDTO that includes the baseEntityLevels.
     * It uses a custom query that eagerly loads the baseEntityLevels to avoid lazy loading issues.
     *
     * @param id The unique identifier of the base entity to retrieve
     * @return A BaseEntityResponseDTO representing the requested base entity with its levels
     * @throws IllegalArgumentException If no base entity with the given ID exists in the database
     */
    public BaseEntityResponseDTO getBaseEntityByIdWithLevels(Long id) {
        return Optional.ofNullable(baseEntityRepository.findByIdWithLevels(id))
                .map(baseEntityMapper::toResponseDTO)
                .orElseThrow(() -> new IllegalArgumentException("BaseEntity not found"));
    }

    /**
     * Retrieves base entity summaries for all entities.
     * This method uses a projection to efficiently fetch only the necessary data
     * without loading the entire entity and its relationships.
     * It's useful for list views and search results where only basic information is needed.
     *
     * @return A list of BaseEntitySummary projections with basic entity information
     */
    public List<BaseEntitySummary> getAllBaseEntitySummaries() {
        return baseEntityRepository.findAllProjectedBy();
    }

    /**
     * Retrieves base entity summaries for entities with the specified category.
     * This method uses a projection to efficiently fetch only the necessary data
     * without loading the entire entity and its relationships.
     * It's useful for list views and search results where only basic information is needed.
     *
     * @param category The category to filter by
     * @return A list of BaseEntitySummary projections with basic entity information
     */
    public List<BaseEntitySummary> getBaseEntitySummariesByCategory(Category category) {
        return baseEntityRepository.findSummaryByCategory(category);
    }
}
