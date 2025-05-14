package at.htlkaindorf.clashtoolsbackend.dto.account;

import lombok.Data;

import java.util.Set;

@Data
public class AccountResponseDTO {
    private Long id;

    private String accountName;

    private Long userId;

    private String username;

    private Set<Long> baseEntityIds;
}
