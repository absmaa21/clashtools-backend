package at.htlkaindorf.clashtoolsbackend.service;

import at.htlkaindorf.clashtoolsbackend.dto.baseentity.BaseEntityLevelRequestDTO;
import at.htlkaindorf.clashtoolsbackend.dto.baseentity.BaseEntityLevelResponseDTO;
import at.htlkaindorf.clashtoolsbackend.dto.baseentity.SimpleBaseEntityLevelRequestDTO;
import at.htlkaindorf.clashtoolsbackend.mapper.BaseEntityLevelMapper;
import at.htlkaindorf.clashtoolsbackend.pojos.*;
import at.htlkaindorf.clashtoolsbackend.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service for managing base entity levels in the system.
 * This service provides methods for creating, retrieving, updating, and deleting base entity levels,
 * which represent specific levels of base entities in the Clash Tools application.
 * It acts as an intermediary between the controller layer and the repository layer,
 * handling business logic and data transformation.
 */
@Service
@RequiredArgsConstructor
public class BaseEntityLevelService {

    private final BaseEntityLevelRepository baseEntityLevelRepository;
    private final BaseEntityRepository baseEntityRepository;
    private final LevelRepository levelRepository;
    private final AttributeRepository attributeRepository;
    private final BaseEntityLevelMapper baseEntityLevelMapper;
    private final AccountRepository accountRepository;

    /**
     * Retrieves all base entity levels from the database.
     * This method fetches all base entity levels stored in the system and converts them to DTOs
     * for use in the presentation layer.
     *
     * @return A list of BaseEntityLevelResponseDTO objects representing all base entity levels in the system
     */
    public List<BaseEntityLevelResponseDTO> getAllBaseEntityLevels() {
        List<BaseEntityLevel> baseEntityLevels = baseEntityLevelRepository.findAll();
        return baseEntityLevelMapper.toResponseDTOList(baseEntityLevels);
    }

    /**
     * Retrieves a specific base entity level by its unique identifier.
     * This method searches for a base entity level with the given ID in the database,
     * throws an exception if not found, and converts it to a DTO for the presentation layer.
     *
     * @param id The unique identifier of the base entity level to retrieve
     * @return A BaseEntityLevelResponseDTO representing the requested base entity level
     * @throws IllegalArgumentException If no base entity level with the given ID exists in the database
     */
    public BaseEntityLevelResponseDTO getBaseEntityLevelById(Long id) {
        BaseEntityLevel baseEntityLevel = baseEntityLevelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Base entity level not found"));
        return baseEntityLevelMapper.toResponseDTO(baseEntityLevel);
    }

    /**
     * Retrieves all base entity levels associated with a specific base entity.
     * This method fetches all base entity levels with the given base entity ID and converts them to DTOs.
     *
     * @param baseEntityId The ID of the base entity to filter by
     * @return A list of BaseEntityLevelResponseDTO objects representing base entity levels with the specified base entity
     * @throws IllegalArgumentException If no base entity with the given ID exists in the database
     */
    public List<BaseEntityLevelResponseDTO> getBaseEntityLevelsByBaseEntityId(Long baseEntityId) {
        // Verify that the base entity exists
        baseEntityRepository.findById(baseEntityId)
                .orElseThrow(() -> new IllegalArgumentException("Base entity not found"));

        List<BaseEntityLevel> baseEntityLevels = baseEntityLevelRepository.findByBaseEntityId(baseEntityId);
        return baseEntityLevelMapper.toResponseDTOList(baseEntityLevels);
    }

    /**
     * Retrieves all base entity levels associated with a specific level.
     * This method fetches all base entity levels with the given level ID and converts them to DTOs.
     *
     * @param levelId The ID of the level to filter by
     * @return A list of BaseEntityLevelResponseDTO objects representing base entity levels with the specified level
     * @throws IllegalArgumentException If no level with the given ID exists in the database
     */
    public List<BaseEntityLevelResponseDTO> getBaseEntityLevelsByLevelId(Long levelId) {
        // Verify that the level exists
        levelRepository.findById(levelId)
                .orElseThrow(() -> new IllegalArgumentException("Level not found"));

        List<BaseEntityLevel> baseEntityLevels = baseEntityLevelRepository.findByLevelId(levelId);
        return baseEntityLevelMapper.toResponseDTOList(baseEntityLevels);
    }

    /**
     * Creates a new base entity level in the database.
     * This method converts the provided request DTO to an entity object,
     * persists it to the database, and returns the created entity as a DTO.
     *
     * @param requestDTO The BaseEntityLevelRequestDTO containing the data for the new base entity level
     * @return A BaseEntityLevelResponseDTO representing the newly created base entity level
     * @throws IllegalArgumentException If the base entity or level with the given IDs do not exist
     * @throws IllegalArgumentException If any of the attributes with the given IDs do not exist
     */
    public BaseEntityLevelResponseDTO createBaseEntityLevel(BaseEntityLevelRequestDTO requestDTO) {
        // Verify that the base entity exists
        BaseEntity baseEntity = baseEntityRepository.findById(requestDTO.getBaseEntityId())
                .orElseThrow(() -> new IllegalArgumentException("Base entity not found"));

        // Verify that the level exists
        Level level = levelRepository.findById(requestDTO.getLevelId())
                .orElseThrow(() -> new IllegalArgumentException("Level not found"));

        // Check if a base entity level with the same base entity and level already exists
        baseEntityLevelRepository.findByBaseEntityAndLevel(baseEntity, level)
                .ifPresent(existingLevel -> {
                    throw new IllegalArgumentException("A base entity level with the same base entity and level already exists");
                });

        // Verify that all attributes exist
        Set<Attribute> attributes = new HashSet<>();
        if (requestDTO.getAttributeIds() != null && !requestDTO.getAttributeIds().isEmpty()) {
            attributes = requestDTO.getAttributeIds().stream()
                    .map(attributeId -> attributeRepository.findById(attributeId)
                            .orElseThrow(() -> new IllegalArgumentException("Attribute not found: " + attributeId)))
                    .collect(Collectors.toSet());
        }

        BaseEntityLevel baseEntityLevel = baseEntityLevelMapper.toEntity(requestDTO);
        baseEntityLevel.setAttributes(attributes);

        // Explicitly set all fields for clarity and consistency
        baseEntityLevel.setResourceType(requestDTO.getResourceType());
        baseEntityLevel.setUpgradeCost(requestDTO.getUpgradeCost());
        baseEntityLevel.setUpgradeTime(requestDTO.getUpgradeTime());
        baseEntityLevel.setImgPath(requestDTO.getImgPath());

        BaseEntityLevel savedBaseEntityLevel = baseEntityLevelRepository.save(baseEntityLevel);
        return baseEntityLevelMapper.toResponseDTO(savedBaseEntityLevel);
    }

    /**
     * Updates an existing base entity level in the database.
     * This method retrieves the base entity level with the given ID, updates its properties
     * with the values from the request DTO, persists the changes to the database,
     * and returns the updated entity as a DTO.
     *
     * @param id The unique identifier of the base entity level to update
     * @param requestDTO The BaseEntityLevelRequestDTO containing the updated data
     * @return A BaseEntityLevelResponseDTO representing the updated base entity level
     * @throws IllegalArgumentException If no base entity level with the given ID exists in the database
     * @throws IllegalArgumentException If the base entity or level with the given IDs do not exist
     * @throws IllegalArgumentException If any of the attributes with the given IDs do not exist
     */
    public BaseEntityLevelResponseDTO updateBaseEntityLevel(Long id, BaseEntityLevelRequestDTO requestDTO) {
        BaseEntityLevel baseEntityLevel = baseEntityLevelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Base entity level not found"));

        // Verify that the base entity exists
        BaseEntity baseEntity = baseEntityRepository.findById(requestDTO.getBaseEntityId())
                .orElseThrow(() -> new IllegalArgumentException("Base entity not found"));

        // Verify that the level exists
        Level level = levelRepository.findById(requestDTO.getLevelId())
                .orElseThrow(() -> new IllegalArgumentException("Level not found"));

        // Check if a base entity level with the same base entity and level already exists (and it's not the one being updated)
        baseEntityLevelRepository.findByBaseEntityAndLevel(baseEntity, level)
                .ifPresent(existingLevel -> {
                    if (!existingLevel.getId().equals(id)) {
                        throw new IllegalArgumentException("A base entity level with the same base entity and level already exists");
                    }
                });

        // Verify that all attributes exist
        Set<Attribute> attributes = new HashSet<>();
        if (requestDTO.getAttributeIds() != null && !requestDTO.getAttributeIds().isEmpty()) {
            attributes = requestDTO.getAttributeIds().stream()
                    .map(attributeId -> attributeRepository.findById(attributeId)
                            .orElseThrow(() -> new IllegalArgumentException("Attribute not found: " + attributeId)))
                    .collect(Collectors.toSet());
        }

        baseEntityLevel.setBaseEntity(baseEntity);
        baseEntityLevel.setLevel(level);
        baseEntityLevel.setAttributes(attributes);

        // Update the additional fields
        baseEntityLevel.setResourceType(requestDTO.getResourceType());
        baseEntityLevel.setUpgradeCost(requestDTO.getUpgradeCost());
        baseEntityLevel.setUpgradeTime(requestDTO.getUpgradeTime());
        baseEntityLevel.setImgPath(requestDTO.getImgPath());

        BaseEntityLevel updatedBaseEntityLevel = baseEntityLevelRepository.save(baseEntityLevel);
        return baseEntityLevelMapper.toResponseDTO(updatedBaseEntityLevel);
    }

    /**
     * Deletes a base entity level from the database.
     * This method removes the base entity level with the given ID from the database.
     * If no entity with the given ID exists, the operation completes silently.
     *
     * @param id The unique identifier of the base entity level to delete
     */
    public void deleteBaseEntityLevel(Long id) {
        baseEntityLevelRepository.deleteById(id);
    }

    /**
     * Deletes all base entity levels associated with a specific base entity.
     * This method removes all base entity levels with the given base entity ID from the database.
     *
     * @param baseEntityId The ID of the base entity whose levels should be deleted
     * @throws IllegalArgumentException If no base entity with the given ID exists in the database
     */
    public void deleteBaseEntityLevelsByBaseEntityId(Long baseEntityId) {
        BaseEntity baseEntity = baseEntityRepository.findById(baseEntityId)
                .orElseThrow(() -> new IllegalArgumentException("Base entity not found"));
        baseEntityLevelRepository.deleteByBaseEntity(baseEntity);
    }

    /**
     * Deletes all base entity levels associated with a specific level.
     * This method removes all base entity levels with the given level ID from the database.
     *
     * @param levelId The ID of the level whose base entity levels should be deleted
     * @throws IllegalArgumentException If no level with the given ID exists in the database
     */
    public void deleteBaseEntityLevelsByLevelId(Long levelId) {
        Level level = levelRepository.findById(levelId)
                .orElseThrow(() -> new IllegalArgumentException("Level not found"));
        baseEntityLevelRepository.deleteByLevel(level);
    }

    /**
     * Creates a new base entity level with a simplified request.
     * This method provides a simpler way to create a base entity level by:
     * 1. Finding the account with the given ID
     * 2. Finding or creating a base entity for the given account and base entity name
     * 3. Creating a new base entity level for the base entity and level
     * 4. Associating the attributes with the base entity level
     *
     * @param requestDTO The SimpleBaseEntityLevelRequestDTO containing the data for the new base entity level
     * @return A BaseEntityLevelResponseDTO representing the newly created base entity level
     * @throws IllegalArgumentException If the account with the given ID does not exist
     * @throws IllegalArgumentException If the level with the given ID does not exist
     * @throws IllegalArgumentException If any of the attributes with the given IDs do not exist
     */
    public BaseEntityLevelResponseDTO createSimpleBaseEntityLevel(SimpleBaseEntityLevelRequestDTO requestDTO) {
        // Verify that the account exists
        Account account = accountRepository.findById(requestDTO.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        // Verify that the level exists
        Level level = levelRepository.findById(requestDTO.getLevelId())
                .orElseThrow(() -> new IllegalArgumentException("Level not found"));

        // Find or create a base entity for the given account and base entity name
        BaseEntity baseEntity = findOrCreateBaseEntity(account, requestDTO.getBaseEntityNameId());

        // Check if a base entity level with the same base entity and level already exists
        baseEntityLevelRepository.findByBaseEntityAndLevel(baseEntity, level)
                .ifPresent(existingLevel -> {
                    throw new IllegalArgumentException("A base entity level with the same base entity and level already exists");
                });

        // Verify that all attributes exist
        Set<Attribute> attributes = new HashSet<>();
        if (requestDTO.getAttributeIds() != null && !requestDTO.getAttributeIds().isEmpty()) {
            attributes = requestDTO.getAttributeIds().stream()
                    .map(attributeId -> attributeRepository.findById(attributeId)
                            .orElseThrow(() -> new IllegalArgumentException("Attribute not found: " + attributeId)))
                    .collect(Collectors.toSet());
        }

        // Create a new base entity level
        BaseEntityLevel baseEntityLevel = new BaseEntityLevel();
        baseEntityLevel.setBaseEntity(baseEntity);
        baseEntityLevel.setLevel(level);
        baseEntityLevel.setAttributes(attributes);

        // Set the additional fields
        baseEntityLevel.setResourceType(requestDTO.getResourceType());
        baseEntityLevel.setUpgradeCost(requestDTO.getUpgradeCost());
        baseEntityLevel.setUpgradeTime(requestDTO.getUpgradeTime());
        baseEntityLevel.setImgPath(requestDTO.getImgPath());

        BaseEntityLevel savedBaseEntityLevel = baseEntityLevelRepository.save(baseEntityLevel);
        return baseEntityLevelMapper.toResponseDTO(savedBaseEntityLevel);
    }

    /**
     * Finds or creates a base entity for the given account and base entity name.
     * This method checks if a base entity with the given account and base entity name already exists.
     * If it does, it returns that base entity. If not, it creates a new one.
     *
     * @param account The account to associate the base entity with
     * @param baseEntityNameId The ID of the base entity name to use for the base entity
     * @return The found or created base entity
     * @throws IllegalArgumentException If the base entity name with the given ID does not exist
     */
    private BaseEntity findOrCreateBaseEntity(Account account, Long baseEntityNameId) {
        // Get all base entities for the account
        List<BaseEntity> accountBaseEntities = baseEntityRepository.findByAccountId(account.getId());

        // Find a base entity with the matching base entity name ID
        for (BaseEntity baseEntity : accountBaseEntities) {
            if (baseEntity.getBaseEntityName() != null &&
                baseEntity.getBaseEntityName().getId().equals(baseEntityNameId)) {
                return baseEntity;
            }
        }

        // If no matching base entity is found, create a new one
        // First, find the base entity name
        BaseEntityName baseEntityName = null;
        // Since we don't have a BaseEntityNameRepository, we need to find it another way
        // We'll look through all base entities to find one with the matching base entity name ID
        for (BaseEntity baseEntity : baseEntityRepository.findAll()) {
            if (baseEntity.getBaseEntityName() != null &&
                baseEntity.getBaseEntityName().getId().equals(baseEntityNameId)) {
                baseEntityName = baseEntity.getBaseEntityName();
                break;
            }
        }

        if (baseEntityName == null) {
            throw new IllegalArgumentException("Base entity name not found");
        }

        // Create a new base entity
        BaseEntity newBaseEntity = new BaseEntity();
        newBaseEntity.setAccount(account);
        newBaseEntity.setBaseEntityName(baseEntityName);
        return baseEntityRepository.save(newBaseEntity);
    }
}
