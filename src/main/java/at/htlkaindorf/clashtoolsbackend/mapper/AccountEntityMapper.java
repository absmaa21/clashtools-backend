package at.htlkaindorf.clashtoolsbackend.mapper;

import at.htlkaindorf.clashtoolsbackend.dto.account.AccountEntityDTO;
import at.htlkaindorf.clashtoolsbackend.pojos.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper interface for converting between AccountEntity entities and DTOs.
 * Extends the generic EntityMapper interface with specific entity and DTO types.
 * Uses BaseEntityMapper to convert BaseEntity to BaseEntityResponseDTO to prevent circular references.
 */
@Mapper(componentModel = "spring", uses = {BaseEntityMapper.class})
public interface AccountEntityMapper extends EntityMapper<AccountEntity, AccountEntityDTO, AccountEntityDTO> {

    /**
     * Converts an AccountEntity to an AccountEntityDTO.
     * Uses BaseEntityMapper to convert BaseEntity to SimpleBaseEntityResponseDTO to prevent circular references.
     * SimpleBaseEntityResponseDTO is a simplified version of BaseEntityResponseDTO without the set of levels,
     * which helps to avoid infinite loops during serialization.
     *
     * @param accountEntity The entity to convert
     * @return The converted DTO
     */
    @Mapping(source = "baseEntity", target = "entity", qualifiedByName = "toSimpleResponseDTO")
    @Mapping(source = "currentLevel", target = "level")
    @Override
    AccountEntityDTO toDTO(AccountEntity accountEntity);

    /**
     * Converts an AccountEntityDTO to an AccountEntity.
     * Note: This method is not recommended for direct use as it may not handle all relationships correctly.
     *
     * @param accountEntityDTO The DTO to convert
     * @return The converted entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "entity", target = "baseEntity")
    @Mapping(source = "level", target = "currentLevel")
    @Mapping(target = "account", ignore = true)
    @Override
    AccountEntity toEntity(AccountEntityDTO accountEntityDTO);

    /**
     * Converts a list of AccountEntity entities to a list of AccountEntityDTOs.
     *
     * @param accountEntities The list of entities to convert
     * @return The list of converted DTOs
     */
    @Override
    default List<AccountEntityDTO> toDTOList(List<AccountEntity> accountEntities) {
        if (accountEntities == null) {
            return null;
        }
        return accountEntities.stream()
                .map(this::toDTO)
                .toList();
    }
}
