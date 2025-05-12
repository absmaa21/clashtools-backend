package at.htlkaindorf.clashtoolsbackend.mapper;

import at.htlkaindorf.clashtoolsbackend.dto.baseentity.BaseEntityDTO;
import at.htlkaindorf.clashtoolsbackend.dto.baseentity.BaseEntityRequestDTO;
import at.htlkaindorf.clashtoolsbackend.pojos.BaseEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BaseEntityMapper {
    BaseEntityDTO toDTO(BaseEntity baseEntity);
    BaseEntity toEntity(BaseEntityRequestDTO requestDTO);
    List<BaseEntityDTO> toDTOList(List<BaseEntity> baseEntities);
}
