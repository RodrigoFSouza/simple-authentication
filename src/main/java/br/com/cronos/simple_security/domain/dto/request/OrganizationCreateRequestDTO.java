package br.com.cronos.simple_security.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationCreateRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "CNPJ is required")
    private String cnpj;

    private String description;

    @NotBlank(message = "Company name is required")
    private String companyName;

    private Integer maxUsers;

    private Long parentOrganizationId;
}
