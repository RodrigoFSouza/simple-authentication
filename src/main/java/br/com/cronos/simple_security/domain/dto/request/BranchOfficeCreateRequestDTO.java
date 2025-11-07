package br.com.cronos.simple_security.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BranchOfficeCreateRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Organization ID is required")
    private Long organizationId;

    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String phone;

    @Email(message = "Invalid email format")
    private String email;
}
