package br.com.cronos.simple_security.mapper;

import br.com.cronos.simple_security.domain.dto.OrganizationDTO;
import br.com.cronos.simple_security.domain.entity.Organization;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrganizationMapper {

    public OrganizationDTO toDto(Organization organization) {
        if (organization == null) {
            return null;
        }
        return OrganizationDTO.builder()
                .id(organization.getId())
                .name(organization.getName())
                .cnpj(organization.getCnpj())
                .description(organization.getDescription())
                .companyName(organization.getCompanyName())
                .maxUsers(organization.getMaxUsers())
                .currentUserCount(organization.getUsers() != null ? organization.getUsers().size() : 0)
                .createdAt(organization.getCreatedAt())
                .updatedAt(organization.getUpdatedAt())
                .build();
    }

    public List<OrganizationDTO> toDto(List<Organization> organizations) {
        return organizations.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
