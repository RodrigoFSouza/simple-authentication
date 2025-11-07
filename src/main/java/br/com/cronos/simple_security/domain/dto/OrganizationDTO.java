package br.com.cronos.simple_security.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationDTO {

    private Long id;
    private String name;
    private String cnpj;
    private String description;
    private String companyName;
    private Integer maxUsers;
    private Long parentOrganizationId;
    private String parentOrganizationName;
    private Integer currentUserCount;
    private Integer subOrganizationsCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
