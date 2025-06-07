package at.htlkaindorf.clashtoolsbackend.mapper;

import at.htlkaindorf.clashtoolsbackend.dto.account.AccountEntityDTO;
import at.htlkaindorf.clashtoolsbackend.pojos.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper interface for converting between AccountEntity entities and DTOs.
 * Extends the generic EntityMapper interface with specific entity and DTO types.
 */
@Mapper(componentModel = "spring")
public interface AccountEntityMapper extends EntityMapper<AccountEntity, AccountEntityDTO, AccountEntityDTO> {

    @Mapping(source = "baseEntity", target = "entity")
    @Mapping(source = "currentLevel", target = "level")
    @Override
    AccountEntityDTO toDTO(AccountEntity accountEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "entity", target = "baseEntity")
    @Mapping(source = "level", target = "currentLevel")
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
