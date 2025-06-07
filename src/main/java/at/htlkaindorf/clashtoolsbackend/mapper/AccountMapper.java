package at.htlkaindorf.clashtoolsbackend.mapper;

import at.htlkaindorf.clashtoolsbackend.dto.account.AccountRequestDTO;
import at.htlkaindorf.clashtoolsbackend.dto.account.AccountResponseDTO;
import at.htlkaindorf.clashtoolsbackend.pojos.Account;
import at.htlkaindorf.clashtoolsbackend.pojos.BaseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AccountMapper extends EntityMapper<Account, AccountResponseDTO, AccountRequestDTO> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.username", target = "username")
    AccountResponseDTO toDTO(Account account);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Account toEntity(AccountRequestDTO requestDTO);

    List<AccountResponseDTO> toDTOList(List<Account> accounts);

    @Named("baseEntitiesToIds")
    default Set<Long> baseEntitiesToIds(Set<BaseEntity> baseEntities) {
        if (baseEntities == null) {
            return null;
        }
        return baseEntities.stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toSet());
    }
}
