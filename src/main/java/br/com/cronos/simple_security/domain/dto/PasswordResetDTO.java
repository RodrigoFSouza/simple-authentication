package br.com.cronos.simple_security.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Password reset information")
public class PasswordResetDTO {
    @Schema(description = "Password reset token", example = "abc123def456")
    @NotBlank(message = "Token cannot be blank")
    private String token;

    @Schema(description = "New password", example = "newPassword123")
    @NotBlank(message = "New password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters long") // Adjust size as needed
    private String newPassword;
} 