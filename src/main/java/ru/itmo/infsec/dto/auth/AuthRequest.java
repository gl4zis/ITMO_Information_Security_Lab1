package ru.itmo.infsec.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthRequest {
    @NotBlank(message = "Login cannot be empty")
    @Pattern(regexp = "^\\w+$", message = "Login can have only letters, digits and '_'")
    private String login;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, message = "Password should have at least 6 symbols")
    private String password;
}
