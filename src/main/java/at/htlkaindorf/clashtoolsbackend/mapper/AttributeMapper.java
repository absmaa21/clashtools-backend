package at.htlkaindorf.clashtoolsbackend.mapper;

import at.htlkaindorf.clashtoolsbackend.dto.attribute.AttributeRequestDTO;
import at.htlkaindorf.clashtoolsbackend.dto.attribute.AttributeResponseDTO;
import at.htlkaindorf.clashtoolsbackend.pojos.Attribute;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttributeMapper extends EntityMapper<Attribute, AttributeResponseDTO, AttributeRequestDTO> {

    @org.mapstruct.Named("mapTranslation")
    @org.mapstruct.Mapping(target = "attribute", ignore = true)
    default at.htlkaindorf.clashtoolsbackend.pojos.AttributeTranslation mapTranslation(at.htlkaindorf.clashtoolsbackend.dto.attribute.AttributeTranslationDTO dto) {
        if (dto == null) {
            return null;
        }
        at.htlkaindorf.clashtoolsbackend.pojos.AttributeTranslation translation = new at.htlkaindorf.clashtoolsbackend.pojos.AttributeTranslation();
        translation.setId(dto.getId());
        translation.setLanguageCode(dto.getLanguageCode());
        translation.setName(dto.getName());
        return translation;
    }
    @Override
    AttributeResponseDTO toDTO(Attribute attribute);

    @Override
    @org.mapstruct.Mapping(source = "attributeNameId", target = "attributeName.id")
    @org.mapstruct.Mapping(target = "id", ignore = true)
    @org.mapstruct.Mapping(target = "baseEntityLevels", ignore = true)
    @org.mapstruct.Mapping(source = "translations", target = "translations", qualifiedByName = "mapTranslation")
    Attribute toEntity(AttributeRequestDTO requestDTO);

    @Override
    List<AttributeResponseDTO> toDTOList(List<Attribute> attributes);
}
