package br.com.cronos.simple_security.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
@Schema(description = "User update information")
public class UserUpdateRequestDTO {
    @Schema(description = "User's first name", example = "John")
    private String firstname;

    @Schema(description = "User's last name", example = "Doe")
    private String lastname;

    @Schema(description = "User's email address", example = "john.doe@example.com")
    @Email
    private String email;

    @Schema(description = "Whether the account is not expired", example = "true")
    private Boolean isAccountNonExpired;

    @Schema(description = "Whether the account is not locked", example = "true")
    private Boolean isAccountNonLocked;

    @Schema(description = "Whether the credentials are not expired", example = "true")
    private Boolean isCredentialsNonExpired;

    @Schema(description = "Whether the account is enabled", example = "true")
    private Boolean isEnabled;
} 