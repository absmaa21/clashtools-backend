package at.htlkaindorf.clashtoolsbackend.service;

import at.htlkaindorf.clashtoolsbackend.dto.baseentity.BaseEntityLevelRequestDTO;
import at.htlkaindorf.clashtoolsbackend.dto.baseentity.BaseEntityLevelResponseDTO;
import at.htlkaindorf.clashtoolsbackend.mapper.BaseEntityLevelMapper;
import at.htlkaindorf.clashtoolsbackend.pojos.Attribute;
import at.htlkaindorf.clashtoolsbackend.pojos.BaseEntity;
import at.htlkaindorf.clashtoolsbackend.pojos.BaseEntityLevel;
import at.htlkaindorf.clashtoolsbackend.repositories.AttributeRepository;
import at.htlkaindorf.clashtoolsbackend.repositories.BaseEntityLevelRepository;
import at.htlkaindorf.clashtoolsbackend.repositories.BaseEntityRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service for managing base entity levels in the system.
 * This service provides methods for creating, retrieving, updating, and deleting base entity levels,
 * which represent specific levels of base entities in the Clash Tools application.
 * It extends AbstractCrudService to inherit common CRUD operations.
 */
@Service
public class BaseEntityLevelService extends AbstractCrudService<BaseEntityLevel, BaseEntityLevelResponseDTO, BaseEntityLevelRequestDTO, Long> {

    private final BaseEntityLevelRepository baseEntityLevelRepository;
    private final BaseEntityRepository baseEntityRepository;
    private final AttributeRepository attributeRepository;
    private final BaseEntityLevelMapper baseEntityLevelMapper;
    public BaseEntityLevelService(BaseEntityLevelRepository baseEntityLevelRepository,
                                 BaseEntityRepository baseEntityRepository,
                                 AttributeRepository attributeRepository,
                                 BaseEntityLevelMapper baseEntityLevelMapper) {
        super(baseEntityLevelRepository, baseEntityLevelMapper);
        this.baseEntityLevelRepository = baseEntityLevelRepository;
        this.baseEntityRepository = baseEntityRepository;
        this.attributeRepository = attributeRepository;
        this.baseEntityLevelMapper = baseEntityLevelMapper;
    }

    @Override
    protected void setEntityId(BaseEntityLevel entity, Long id) {
        entity.setId(id);
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
        baseEntityRepository.findById(baseEntityId)
                .orElseThrow(() -> new IllegalArgumentException("Base entity not found"));

        List<BaseEntityLevel> baseEntityLevels = baseEntityLevelRepository.findByBaseEntityId(baseEntityId);
        return baseEntityLevelMapper.toDTOList(baseEntityLevels);
    }


    /**
     * Creates a new base entity level in the database.
     * This method overrides the default implementation to add validation and handle relationships.
     *
     * @param requestDTO The BaseEntityLevelRequestDTO containing the data for the new base entity level
     * @return A BaseEntityLevelResponseDTO representing the newly created base entity level
     * @throws IllegalArgumentException If the base entity with the given ID does not exist
     * @throws IllegalArgumentException If any of the attributes with the given IDs do not exist
     */
    @Override
    public BaseEntityLevelResponseDTO create(BaseEntityLevelRequestDTO requestDTO) {
        BaseEntity baseEntity = baseEntityRepository.findById(requestDTO.getBaseEntityId())
                .orElseThrow(() -> new IllegalArgumentException("Base entity not found"));

        baseEntityLevelRepository.findByBaseEntityAndLevel(baseEntity, requestDTO.getLevel())
                .ifPresent(existingLevel -> {
                    throw new IllegalArgumentException("A base entity level with the same base entity and level already exists");
                });

        Set<Attribute> attributes = new HashSet<>();
        if (requestDTO.getAttributeIds() != null && !requestDTO.getAttributeIds().isEmpty()) {
            attributes = requestDTO.getAttributeIds().stream()
                    .map(attributeId -> attributeRepository.findById(attributeId)
                            .orElseThrow(() -> new IllegalArgumentException("Attribute not found: " + attributeId)))
                    .collect(Collectors.toSet());
        }

        BaseEntityLevel baseEntityLevel = baseEntityLevelMapper.toEntity(requestDTO);
        baseEntityLevel.setAttributes(attributes);

        baseEntityLevel.setResourceType(requestDTO.getResourceType());
        baseEntityLevel.setUpgradeCost(requestDTO.getUpgradeCost());
        baseEntityLevel.setUpgradeTime(requestDTO.getUpgradeTime());
        baseEntityLevel.setImgPath(requestDTO.getImgPath());

        BaseEntityLevel savedBaseEntityLevel = baseEntityLevelRepository.save(baseEntityLevel);
        return baseEntityLevelMapper.toDTO(savedBaseEntityLevel);
    }

    /**
     * Updates an existing base entity level in the database.
     * This method overrides the default implementation to add validation and handle relationships.
     *
     * @param id The unique identifier of the base entity level to update
     * @param requestDTO The BaseEntityLevelRequestDTO containing the updated data
     * @return A BaseEntityLevelResponseDTO representing the updated base entity level
     * @throws IllegalArgumentException If no base entity level with the given ID exists in the database
     * @throws IllegalArgumentException If the base entity with the given ID does not exist
     * @throws IllegalArgumentException If any of the attributes with the given IDs do not exist
     */
    @Override
    public BaseEntityLevelResponseDTO update(Long id, BaseEntityLevelRequestDTO requestDTO) {
        BaseEntityLevel baseEntityLevel = baseEntityLevelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Base entity level not found"));

        BaseEntity baseEntity = baseEntityRepository.findById(requestDTO.getBaseEntityId())
                .orElseThrow(() -> new IllegalArgumentException("Base entity not found"));

        baseEntityLevelRepository.findByBaseEntityAndLevel(baseEntity, requestDTO.getLevel())
                .ifPresent(existingLevel -> {
                    if (!existingLevel.getId().equals(id)) {
                        throw new IllegalArgumentException("A base entity level with the same base entity and level already exists");
                    }
                });

        Set<Attribute> attributes = new HashSet<>();
        if (requestDTO.getAttributeIds() != null && !requestDTO.getAttributeIds().isEmpty()) {
            attributes = requestDTO.getAttributeIds().stream()
                    .map(attributeId -> attributeRepository.findById(attributeId)
                            .orElseThrow(() -> new IllegalArgumentException("Attribute not found: " + attributeId)))
                    .collect(Collectors.toSet());
        }

        baseEntityLevel.setBaseEntity(baseEntity);
        baseEntityLevel.setLevel(requestDTO.getLevel());
        baseEntityLevel.setAttributes(attributes);

        baseEntityLevel.setResourceType(requestDTO.getResourceType());
        baseEntityLevel.setUpgradeCost(requestDTO.getUpgradeCost());
        baseEntityLevel.setUpgradeTime(requestDTO.getUpgradeTime());
        baseEntityLevel.setImgPath(requestDTO.getImgPath());

        BaseEntityLevel updatedBaseEntityLevel = baseEntityLevelRepository.save(baseEntityLevel);
        return baseEntityLevelMapper.toDTO(updatedBaseEntityLevel);
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

}
