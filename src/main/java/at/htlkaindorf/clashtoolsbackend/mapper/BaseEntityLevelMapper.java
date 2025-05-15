package at.htlkaindorf.clashtoolsbackend.mapper;

import at.htlkaindorf.clashtoolsbackend.dto.baseentity.BaseEntityLevelRequestDTO;
import at.htlkaindorf.clashtoolsbackend.dto.baseentity.BaseEntityLevelResponseDTO;
import at.htlkaindorf.clashtoolsbackend.pojos.Attribute;
import at.htlkaindorf.clashtoolsbackend.pojos.BaseEntity;
import at.htlkaindorf.clashtoolsbackend.pojos.BaseEntityLevel;
import at.htlkaindorf.clashtoolsbackend.pojos.Level;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {BaseEntityMapper.class, AttributeMapper.class})
public interface BaseEntityLevelMapper {

    @Mapping(source = "baseEntity", target = "baseEntity")
    @Mapping(source = "level", target = "level")
    @Mapping(source = "attributes", target = "attributes")
    BaseEntityLevelResponseDTO toResponseDTO(BaseEntityLevel baseEntityLevel);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "baseEntityId", target = "baseEntity", qualifiedByName = "baseEntityIdToEntity")
    @Mapping(source = "levelId", target = "level", qualifiedByName = "levelIdToEntity")
    @Mapping(source = "attributeIds", target = "attributes", qualifiedByName = "attributeIdsToEntities")
    BaseEntityLevel toEntity(BaseEntityLevelRequestDTO requestDTO);

    List<BaseEntityLevelResponseDTO> toResponseDTOList(List<BaseEntityLevel> baseEntityLevels);

    @Named("baseEntityIdToEntity")
    default BaseEntity baseEntityIdToEntity(Long id) {
        if (id == null) {
            return null;
        }
        BaseEntity baseEntity = new BaseEntity();
        baseEntity.setId(id);
        return baseEntity;
    }

    @Named("levelIdToEntity")
    default Level levelIdToEntity(Long id) {
        if (id == null) {
            return null;
        }
        Level level = new Level();
        level.setId(id);
        return level;
    }

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
