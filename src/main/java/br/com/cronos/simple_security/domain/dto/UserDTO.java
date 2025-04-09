package br.com.cronos.simple_security.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "User information")
public class UserDTO {
    @Schema(description = "User ID", example = "1")
    private Long id;

    @Schema(description = "User's first name", example = "John")
    private String firstname;

    @Schema(description = "User's last name", example = "Doe")
    private String lastname;

    @Schema(description = "User's email address", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Whether the account is not expired", example = "true")
    private boolean isAccountNonExpired;

    @Schema(description = "Whether the account is not locked", example = "true")
    private boolean isAccountNonLocked;

    @Schema(description = "Whether the credentials are not expired", example = "true")
    private boolean isCredentialsNonExpired;

    @Schema(description = "Whether the account is enabled", example = "true")
    private boolean isEnabled;
} 