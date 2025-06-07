package at.htlkaindorf.clashtoolsbackend.mapper;

import at.htlkaindorf.clashtoolsbackend.dto.attribute.AttributeRequestDTO;
import at.htlkaindorf.clashtoolsbackend.dto.attribute.AttributeResponseDTO;
import at.htlkaindorf.clashtoolsbackend.pojos.Attribute;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttributeMapper extends EntityMapper<Attribute, AttributeResponseDTO, AttributeRequestDTO> {
    @Override
    AttributeResponseDTO toDTO(Attribute attribute);
    @Override
    Attribute toEntity(AttributeRequestDTO requestDTO);
    @Override
    List<AttributeResponseDTO> toDTOList(List<Attribute> attributes);
}
