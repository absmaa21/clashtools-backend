package at.htlkaindorf.clashtoolsbackend.mapper;

import at.htlkaindorf.clashtoolsbackend.dto.baseentity.BaseEntityDTO;
import at.htlkaindorf.clashtoolsbackend.dto.baseentity.BaseEntityRequestDTO;
import at.htlkaindorf.clashtoolsbackend.dto.baseentity.BaseEntityResponseDTO;
import at.htlkaindorf.clashtoolsbackend.dto.baseentity.SimpleBaseEntityResponseDTO;
import at.htlkaindorf.clashtoolsbackend.pojos.BaseEntity;
import at.htlkaindorf.clashtoolsbackend.pojos.Category;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper interface for converting between BaseEntity entities and DTOs.
 * Extends the generic EntityMapper interface with specific entity and DTO types.
 */
@Mapper(componentModel = "spring", uses = {SimplifiedBaseEntityLevelMapper.class})
public interface BaseEntityMapper extends EntityMapper<BaseEntity, BaseEntityDTO, BaseEntityRequestDTO> {
    @org.mapstruct.Mapping(target = "categoryId", expression = "java(mapCategoryToInteger(baseEntity.getCategory()))")
    @org.mapstruct.Mapping(target = "level", ignore = true)
    BaseEntityDTO toDTO(BaseEntity baseEntity);

    @org.mapstruct.Named("toResponseDTO")
    @org.mapstruct.Mapping(target = "categoryId", expression = "java(mapCategoryToInteger(baseEntity.getCategory()))")
    @org.mapstruct.Mapping(target = "baseEntityLevels", source = "baseEntityLevels")
    @org.mapstruct.Mapping(target = "level", ignore = true)
    BaseEntityResponseDTO toResponseDTO(BaseEntity baseEntity);

    List<BaseEntityResponseDTO> toResponseDTOList(List<BaseEntity> baseEntities);

    /**
     * Internal method used by toResponseDTOList to convert a BaseEntity to a BaseEntityResponseDTO.
     * This method ensures that the category field is properly mapped to categoryId.
     *
     * @param baseEntity The entity to convert
     * @return The converted DTO
     */
    @org.mapstruct.Mapping(target = "categoryId", expression = "java(mapCategoryToInteger(baseEntity.getCategory()))")
    @org.mapstruct.Mapping(target = "baseEntityLevels", source = "baseEntityLevels")
    @org.mapstruct.Mapping(target = "level", constant = "0")
    BaseEntityResponseDTO baseEntityToBaseEntityResponseDTO(BaseEntity baseEntity);

    @org.mapstruct.Mapping(target = "id", ignore = true)
    @org.mapstruct.Mapping(target = "baseEntityLevels", ignore = true)
    BaseEntity toEntity(BaseEntityRequestDTO requestDTO);
    List<BaseEntityDTO> toDTOList(List<BaseEntity> baseEntities);

    /**
     * Maps the Category enum to its ordinal value (integer)
     * @param category the Category enum
     * @return the ordinal value as Integer
     */
    default Integer mapCategoryToInteger(Category category) {
        return category != null ? category.ordinal() : null;
    }

    /**
     * Converts a BaseEntity to a SimpleBaseEntityResponseDTO.
     * This method creates a simplified version of BaseEntityResponseDTO without the set of levels,
     * to prevent circular references.
     *
     * @param baseEntity The entity to convert
     * @return The converted simplified DTO
     */
    @org.mapstruct.Named("toSimpleResponseDTO")
    @org.mapstruct.Mapping(target = "categoryId", expression = "java(mapCategoryToInteger(baseEntity.getCategory()))")
    @org.mapstruct.Mapping(target = "level", ignore = true)
    SimpleBaseEntityResponseDTO toSimpleResponseDTO(BaseEntity baseEntity);
}
