package br.com.cronos.simple_security.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCreateRequestDTO {
    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    @NotBlank @Email
    private String email;
    @NotBlank @Size(min = 6)
    private String password;
} 