package at.htlkaindorf.clashtoolsbackend.mapper;

import at.htlkaindorf.clashtoolsbackend.dto.attribute.AttributeRequestDTO;
import at.htlkaindorf.clashtoolsbackend.dto.attribute.AttributeResponseDTO;
import at.htlkaindorf.clashtoolsbackend.pojos.Attribute;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttributeMapper {
    AttributeResponseDTO toDTO(Attribute attribute);
    Attribute toEntity(AttributeRequestDTO requestDTO);
    List<AttributeResponseDTO> toDTOList(List<Attribute> attributes);
}
