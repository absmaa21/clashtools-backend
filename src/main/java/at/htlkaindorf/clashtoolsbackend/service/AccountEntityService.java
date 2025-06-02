package at.htlkaindorf.clashtoolsbackend.service;

import at.htlkaindorf.clashtoolsbackend.dto.account.AccountEntityDTO;
import at.htlkaindorf.clashtoolsbackend.mapper.AccountEntityMapper;
import at.htlkaindorf.clashtoolsbackend.pojos.AccountEntity;
import at.htlkaindorf.clashtoolsbackend.repositories.AccountEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountEntityService {

    private final AccountEntityRepository accountEntityRepository;
    private final AccountEntityMapper accountEntityMapper;

    public List<AccountEntityDTO> getAllAccEntities(Long accountId) {
        List<AccountEntity> accountEntities = accountEntityRepository.findAllByAccountId(accountId);
        return accountEntities.stream().map(accountEntityMapper::toDTO).toList();
    }

}
