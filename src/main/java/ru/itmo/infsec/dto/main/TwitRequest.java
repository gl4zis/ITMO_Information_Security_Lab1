package ru.itmo.infsec.dto.main;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class TwitRequest {

    @NotBlank(message = "Message cannot be empty")
    @Size(max = 2000, message = "Message too long")
    private String message;

    @Valid
    @Size(max = 10, message = "Too many tags")
    private Set<@Pattern(regexp = "^\\w+$", message = "Tags can have only letters, digits and '_'") String>
            tags = Set.of();
}
