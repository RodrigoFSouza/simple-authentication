package br.com.cronos.simple_security.service.impl;

import br.com.cronos.simple_security.domain.dto.OrganizationDTO;
import br.com.cronos.simple_security.domain.dto.request.OrganizationCreateRequestDTO;
import br.com.cronos.simple_security.domain.dto.request.OrganizationUpdateRequestDTO;
import br.com.cronos.simple_security.domain.entity.Organization;
import br.com.cronos.simple_security.exception.CnpjAlreadyExistsException;
import br.com.cronos.simple_security.exception.InvalidCnpjException;
import br.com.cronos.simple_security.exception.ResourceNotFoundException;
import br.com.cronos.simple_security.mapper.OrganizationMapper;
import br.com.cronos.simple_security.repository.OrganizationRepository;
import br.com.cronos.simple_security.service.OrganizationService;
import br.com.cronos.simple_security.util.CnpjValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;

    @Override
    @Transactional
    public OrganizationDTO createOrganization(OrganizationCreateRequestDTO request) {
        // Validate CNPJ
        String cleanCnpj = CnpjValidator.clean(request.getCnpj());
        if (!CnpjValidator.isValid(cleanCnpj)) {
            throw new InvalidCnpjException("Invalid CNPJ: " + request.getCnpj());
        }

        // Check if CNPJ already exists
        if (organizationRepository.existsByCnpj(cleanCnpj)) {
            throw new CnpjAlreadyExistsException("Organization with CNPJ " + request.getCnpj() + " already exists");
        }

        Organization organization = Organization.builder()
                .name(request.getName())
                .cnpj(cleanCnpj)
                .description(request.getDescription())
                .companyName(request.getCompanyName())
                .maxUsers(request.getMaxUsers())
                .build();

        Organization saved = organizationRepository.save(organization);
        return organizationMapper.toDto(saved);
    }

    @Override
    @Transactional
    public OrganizationDTO updateOrganization(Long id, OrganizationUpdateRequestDTO request) {
        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found with id: " + id));

        if (request.getName() != null) {
            organization.setName(request.getName());
        }
        if (request.getDescription() != null) {
            organization.setDescription(request.getDescription());
        }
        if (request.getCompanyName() != null) {
            organization.setCompanyName(request.getCompanyName());
        }
        if (request.getMaxUsers() != null) {
            organization.setMaxUsers(request.getMaxUsers());
        }

        Organization updated = organizationRepository.save(organization);
        return organizationMapper.toDto(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public OrganizationDTO findById(Long id) {
        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found with id: " + id));
        return organizationMapper.toDto(organization);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrganizationDTO> findAll(Pageable pageable) {
        return organizationRepository.findAll(pageable)
                .map(organizationMapper::toDto);
    }

    @Override
    @Transactional
    public void deleteOrganization(Long id) {
        if (!organizationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Organization not found with id: " + id);
        }
        organizationRepository.deleteById(id);
    }
}
