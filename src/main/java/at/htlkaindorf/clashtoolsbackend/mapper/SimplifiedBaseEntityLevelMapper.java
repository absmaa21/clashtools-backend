package at.htlkaindorf.clashtoolsbackend.mapper;

import at.htlkaindorf.clashtoolsbackend.dto.baseentity.SimplifiedBaseEntityLevelDTO;
import at.htlkaindorf.clashtoolsbackend.pojos.BaseEntityLevel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

/**
 * Mapper interface for converting between BaseEntityLevel entities and SimplifiedBaseEntityLevelDTO.
 * This mapper handles the conversion of BaseEntityLevel entities to simplified DTOs
 * without including the reference back to the base entity to avoid circular references.
 */
@Mapper(componentModel = "spring", uses = {AttributeMapper.class})
public interface SimplifiedBaseEntityLevelMapper {

    /**
     * Converts a BaseEntityLevel entity to a SimplifiedBaseEntityLevelDTO.
     *
     * @param baseEntityLevel The entity to convert
     * @return The converted simplified DTO
     */
    @Mapping(source = "level", target = "level")
    @Mapping(source = "attributes", target = "attributes")
    @Mapping(source = "resourceType", target = "resourceType")
    @Mapping(source = "upgradeCost", target = "upgradeCost")
    @Mapping(source = "upgradeTime", target = "upgradeTime")
    @Mapping(source = "imgPath", target = "imgPath")
    SimplifiedBaseEntityLevelDTO toDTO(BaseEntityLevel baseEntityLevel);

    /**
     * Converts a set of BaseEntityLevel entities to a set of SimplifiedBaseEntityLevelDTOs.
     *
     * @param baseEntityLevels The set of entities to convert
     * @return The set of converted simplified DTOs
     */
    Set<SimplifiedBaseEntityLevelDTO> toDTOSet(Set<BaseEntityLevel> baseEntityLevels);
}
