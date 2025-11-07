package br.com.cronos.simple_security.service;

import br.com.cronos.simple_security.domain.dto.OrganizationDTO;
import br.com.cronos.simple_security.domain.dto.request.OrganizationCreateRequestDTO;
import br.com.cronos.simple_security.domain.dto.request.OrganizationUpdateRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrganizationService {

    OrganizationDTO createOrganization(OrganizationCreateRequestDTO request);

    OrganizationDTO updateOrganization(Long id, OrganizationUpdateRequestDTO request);

    OrganizationDTO findById(Long id);

    Page<OrganizationDTO> findAll(Pageable pageable);

    List<OrganizationDTO> findSubOrganizations(Long parentOrganizationId);

    void deleteOrganization(Long id);
}
