package at.htlkaindorf.clashtoolsbackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank
    private String username;

    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$",
            message = "Password must be at least 8 characters long, contain uppercase and lowercase" +
                    " letters, a number, and a special character"
    )
    @NotBlank
    private String password;

    @Email(message = "Must be a valid email")
    @NotBlank
    private String email;
}
