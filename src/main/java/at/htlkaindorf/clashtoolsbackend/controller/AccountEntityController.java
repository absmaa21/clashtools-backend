package at.htlkaindorf.clashtoolsbackend.controller;

import at.htlkaindorf.clashtoolsbackend.dto.account.AccountEntityDTO;
import at.htlkaindorf.clashtoolsbackend.pojos.AccountEntity;
import at.htlkaindorf.clashtoolsbackend.service.AccountEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/account-entity")
@RequiredArgsConstructor
public class AccountEntityController {

    private final AccountEntityService accountEntityService;

    @GetMapping("/{accountId}")
    public ResponseEntity<List<AccountEntityDTO>> getAccEntitiesByAccountId(
            @PathVariable Long accountId
    ) {
        List<AccountEntityDTO> accountEntityDTOS = accountEntityService.getAllAccEntities(accountId);
        return ResponseEntity.ok(accountEntityDTOS);
    }
}
