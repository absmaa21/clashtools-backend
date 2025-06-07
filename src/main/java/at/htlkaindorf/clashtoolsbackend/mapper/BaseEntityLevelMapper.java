package at.htlkaindorf.clashtoolsbackend.mapper;

import at.htlkaindorf.clashtoolsbackend.dto.baseentity.BaseEntityLevelRequestDTO;
import at.htlkaindorf.clashtoolsbackend.dto.baseentity.BaseEntityLevelResponseDTO;
import at.htlkaindorf.clashtoolsbackend.pojos.Attribute;
import at.htlkaindorf.clashtoolsbackend.pojos.BaseEntity;
import at.htlkaindorf.clashtoolsbackend.pojos.BaseEntityLevel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper interface for converting between BaseEntityLevel entities and DTOs.
 * This mapper handles the conversion of BaseEntityLevel entities to response DTOs
 * and request DTOs to entities, including the mapping of related entities.
 * Extends the generic EntityMapper interface with specific entity and DTO types.
 */
@Mapper(componentModel = "spring", uses = {BaseEntityMapper.class, AttributeMapper.class})
public interface BaseEntityLevelMapper extends EntityMapper<BaseEntityLevel, BaseEntityLevelResponseDTO, BaseEntityLevelRequestDTO> {

    /**
     * Converts a BaseEntityLevel entity to a BaseEntityLevelResponseDTO.
     * This method overrides the toDTO method from EntityMapper.
     *
     * @param entity The entity to convert
     * @return The converted response DTO
     */
    @Override
    @Mapping(source = "baseEntity", target = "baseEntity")
    @Mapping(source = "level", target = "level")
    @Mapping(source = "attributes", target = "attributes")
    @Mapping(source = "resourceType", target = "resourceType")
    @Mapping(source = "upgradeCost", target = "upgradeCost")
    @Mapping(source = "upgradeTime", target = "upgradeTime")
    @Mapping(source = "imgPath", target = "imgPath")
    BaseEntityLevelResponseDTO toDTO(BaseEntityLevel entity);

    /**
     * Converts a BaseEntityLevelRequestDTO to a BaseEntityLevel entity.
     *
     * @param requestDTO The request DTO to convert
     * @return The converted entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "baseEntityId", target = "baseEntity", qualifiedByName = "baseEntityIdToEntity")
    @Mapping(source = "level", target = "level")
    @Mapping(source = "attributeIds", target = "attributes", qualifiedByName = "attributeIdsToEntities")
    @Mapping(source = "resourceType", target = "resourceType")
    @Mapping(source = "upgradeCost", target = "upgradeCost")
    @Mapping(source = "upgradeTime", target = "upgradeTime")
    @Mapping(source = "imgPath", target = "imgPath")
    BaseEntityLevel toEntity(BaseEntityLevelRequestDTO requestDTO);

    /**
     * Converts a base entity ID to a BaseEntity entity.
     *
     * @param id The base entity ID
     * @return The BaseEntity entity with the given ID
     */
    @Named("baseEntityIdToEntity")
    default BaseEntity baseEntityIdToEntity(Long id) {
        if (id == null) {
            return null;
        }
        BaseEntity baseEntity = new BaseEntity();
        baseEntity.setId(id);
        return baseEntity;
    }

    /**
     * Maps the level value directly.
     *
     * @param level The level value
     * @return The level value
     */
    @Named("levelIdToEntity")
    default Integer levelIdToEntity(Integer level) {
        return level;
    }

    /**
     * Converts a set of attribute IDs to a set of Attribute entities.
     *
     * @param ids The set of attribute IDs
     * @return The set of Attribute entities with the given IDs
     */
    @Named("attributeIdsToEntities")
    default Set<Attribute> attributeIdsToEntities(Set<Long> ids) {
        if (ids == null) {
            return null;
        }
        return ids.stream()
                .map(id -> {
                    Attribute attribute = new Attribute();
                    attribute.setId(id);
                    return attribute;
                })
                .collect(Collectors.toSet());
    }
}
