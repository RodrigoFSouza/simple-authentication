package br.com.cronos.simple_security.domain.dto.request;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BranchOfficeUpdateRequestDTO {

    private String name;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String phone;

    @Email(message = "Invalid email format")
    private String email;
}
