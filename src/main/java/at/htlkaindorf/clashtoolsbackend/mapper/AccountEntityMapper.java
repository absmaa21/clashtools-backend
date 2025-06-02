package at.htlkaindorf.clashtoolsbackend.mapper;

import at.htlkaindorf.clashtoolsbackend.dto.account.AccountEntityDTO;
import at.htlkaindorf.clashtoolsbackend.pojos.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountEntityMapper {

    @Mapping(source = "baseEntity", target = "entity")
    @Mapping(source = "currentLevel", target = "level")
    AccountEntityDTO toDTO(AccountEntity accountEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "entity", target = "baseEntity")
    @Mapping(source = "level", target = "currentLevel")
    AccountEntity toEntity(AccountEntityDTO accountEntityDTO);

}
