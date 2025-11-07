package br.com.cronos.simple_security.mapper;

import br.com.cronos.simple_security.domain.dto.BranchOfficeDTO;
import br.com.cronos.simple_security.domain.entity.BranchOffice;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BranchOfficeMapper {

    public BranchOfficeDTO toDto(BranchOffice branchOffice) {
        if (branchOffice == null) {
            return null;
        }
        return BranchOfficeDTO.builder()
                .id(branchOffice.getId())
                .name(branchOffice.getName())
                .organizationId(branchOffice.getOrganization() != null ? branchOffice.getOrganization().getId() : null)
                .organizationName(branchOffice.getOrganization() != null ? branchOffice.getOrganization().getName() : null)
                .address(branchOffice.getAddress())
                .city(branchOffice.getCity())
                .state(branchOffice.getState())
                .zipCode(branchOffice.getZipCode())
                .phone(branchOffice.getPhone())
                .email(branchOffice.getEmail())
                .createdAt(branchOffice.getCreatedAt())
                .updatedAt(branchOffice.getUpdatedAt())
                .build();
    }

    public List<BranchOfficeDTO> toDto(List<BranchOffice> branchOffices) {
        return branchOffices.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
