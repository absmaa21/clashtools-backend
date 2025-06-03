package at.htlkaindorf.clashtoolsbackend.mapper;

import at.htlkaindorf.clashtoolsbackend.dto.baseentity.BaseEntityDTO;
import at.htlkaindorf.clashtoolsbackend.dto.baseentity.BaseEntityRequestDTO;
import at.htlkaindorf.clashtoolsbackend.pojos.BaseEntity;
import at.htlkaindorf.clashtoolsbackend.pojos.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BaseEntityMapper {
    @org.mapstruct.Mapping(target = "categoryId", expression = "java(mapCategoryToInteger(baseEntity.getCategory()))")
    BaseEntityDTO toDTO(BaseEntity baseEntity);
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
}
