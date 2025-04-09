package br.com.cronos.simple_security.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Password reset request information")
public class PasswordResetRequestDTO {
    @Schema(description = "User's email address", example = "john.doe@example.com")
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;
} 