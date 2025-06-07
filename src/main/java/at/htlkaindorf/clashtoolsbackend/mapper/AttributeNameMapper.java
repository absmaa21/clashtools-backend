package at.htlkaindorf.clashtoolsbackend.mapper;

import at.htlkaindorf.clashtoolsbackend.dto.attribute.AttributeNameDTO;
import at.htlkaindorf.clashtoolsbackend.pojos.AttributeName;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for converting between AttributeName entities and DTOs.
 * This interface uses MapStruct to generate the implementation at compile time.
 */
@Mapper(componentModel = "spring")
public interface AttributeNameMapper extends EntityMapper<AttributeName, AttributeNameDTO, AttributeNameDTO> {

    /**
     * Converts an AttributeName entity to an AttributeNameDTO.
     *
     * @param attributeName The AttributeName entity to convert
     * @return The corresponding AttributeNameDTO
     */
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    AttributeNameDTO toDTO(AttributeName attributeName);

    /**
     * Converts an AttributeNameDTO to an AttributeName entity.
     *
     * @param dto The AttributeNameDTO to convert
     * @return The corresponding AttributeName entity
     */
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "attributes", ignore = true)
    AttributeName toEntity(AttributeNameDTO dto);

    /**
     * Converts a list of AttributeName entities to a list of AttributeNameDTOs.
     *
     * @param attributeNames The list of AttributeName entities to convert
     * @return The corresponding list of AttributeNameDTOs
     */
    List<AttributeNameDTO> toDTOList(List<AttributeName> attributeNames);
}
