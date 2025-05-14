package at.htlkaindorf.clashtoolsbackend.dto.account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccountRequestDTO {
    @NotBlank
    private String accountName;

    @NotNull
    private Long userId;
}
