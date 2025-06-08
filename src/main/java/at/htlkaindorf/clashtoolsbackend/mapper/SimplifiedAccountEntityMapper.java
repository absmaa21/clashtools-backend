package at.htlkaindorf.clashtoolsbackend.mapper;

import at.htlkaindorf.clashtoolsbackend.dto.account.SimplifiedAccountEntityDTO;
import at.htlkaindorf.clashtoolsbackend.pojos.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper interface for converting between AccountEntity entities and SimplifiedAccountEntityDTO.
 * This mapper extracts only the necessary information from BaseEntity to prevent circular references.
 */
@Mapper(componentModel = "spring")
public interface SimplifiedAccountEntityMapper {

    /**
     * Converts an AccountEntity to a SimplifiedAccountEntityDTO.
     * Maps only the necessary fields from BaseEntity to prevent circular references.
     *
     * @param accountEntity The entity to convert
     * @return The simplified DTO
     */
    @Mapping(source = "id", target = "id")
    @Mapping(source = "baseEntity.id", target = "entityId")
    @Mapping(source = "baseEntity.name", target = "entityName")
    @Mapping(source = "currentLevel", target = "level")
    @Mapping(source = "upgradeStart", target = "upgradeStart")
    SimplifiedAccountEntityDTO toDTO(AccountEntity accountEntity);

    /**
     * Converts a list of AccountEntity entities to an array of SimplifiedAccountEntityDTOs.
     *
     * @param accountEntities The list of entities to convert
     * @return The array of simplified DTOs
     */
    SimplifiedAccountEntityDTO[] toDTOList(List<AccountEntity> accountEntities);
}
