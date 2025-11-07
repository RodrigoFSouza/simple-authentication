package br.com.cronos.simple_security.service;

import br.com.cronos.simple_security.domain.dto.BranchOfficeDTO;
import br.com.cronos.simple_security.domain.dto.request.BranchOfficeCreateRequestDTO;
import br.com.cronos.simple_security.domain.dto.request.BranchOfficeUpdateRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BranchOfficeService {

    BranchOfficeDTO createBranchOffice(BranchOfficeCreateRequestDTO request);

    BranchOfficeDTO updateBranchOffice(Long id, BranchOfficeUpdateRequestDTO request);

    BranchOfficeDTO findById(Long id);

    Page<BranchOfficeDTO> findAll(Pageable pageable);

    List<BranchOfficeDTO> findByOrganizationId(Long organizationId);

    void deleteBranchOffice(Long id);
}
