package br.com.cronos.simple_security.service.impl;

import br.com.cronos.simple_security.domain.dto.BranchOfficeDTO;
import br.com.cronos.simple_security.domain.dto.request.BranchOfficeCreateRequestDTO;
import br.com.cronos.simple_security.domain.dto.request.BranchOfficeUpdateRequestDTO;
import br.com.cronos.simple_security.domain.entity.BranchOffice;
import br.com.cronos.simple_security.domain.entity.Organization;
import br.com.cronos.simple_security.exception.ResourceNotFoundException;
import br.com.cronos.simple_security.mapper.BranchOfficeMapper;
import br.com.cronos.simple_security.repository.BranchOfficeRepository;
import br.com.cronos.simple_security.repository.OrganizationRepository;
import br.com.cronos.simple_security.service.BranchOfficeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchOfficeServiceImpl implements BranchOfficeService {

    private final BranchOfficeRepository branchOfficeRepository;
    private final OrganizationRepository organizationRepository;
    private final BranchOfficeMapper branchOfficeMapper;

    @Override
    @Transactional
    public BranchOfficeDTO createBranchOffice(BranchOfficeCreateRequestDTO request) {
        Organization organization = organizationRepository.findById(request.getOrganizationId())
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found with id: " + request.getOrganizationId()));

        BranchOffice branchOffice = BranchOffice.builder()
                .name(request.getName())
                .organization(organization)
                .address(request.getAddress())
                .city(request.getCity())
                .state(request.getState())
                .zipCode(request.getZipCode())
                .phone(request.getPhone())
                .email(request.getEmail())
                .build();

        BranchOffice saved = branchOfficeRepository.save(branchOffice);
        return branchOfficeMapper.toDto(saved);
    }

    @Override
    @Transactional
    public BranchOfficeDTO updateBranchOffice(Long id, BranchOfficeUpdateRequestDTO request) {
        BranchOffice branchOffice = branchOfficeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch office not found with id: " + id));

        if (request.getName() != null) {
            branchOffice.setName(request.getName());
        }
        if (request.getAddress() != null) {
            branchOffice.setAddress(request.getAddress());
        }
        if (request.getCity() != null) {
            branchOffice.setCity(request.getCity());
        }
        if (request.getState() != null) {
            branchOffice.setState(request.getState());
        }
        if (request.getZipCode() != null) {
            branchOffice.setZipCode(request.getZipCode());
        }
        if (request.getPhone() != null) {
            branchOffice.setPhone(request.getPhone());
        }
        if (request.getEmail() != null) {
            branchOffice.setEmail(request.getEmail());
        }

        BranchOffice updated = branchOfficeRepository.save(branchOffice);
        return branchOfficeMapper.toDto(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public BranchOfficeDTO findById(Long id) {
        BranchOffice branchOffice = branchOfficeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch office not found with id: " + id));
        return branchOfficeMapper.toDto(branchOffice);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BranchOfficeDTO> findAll(Pageable pageable) {
        return branchOfficeRepository.findAll(pageable)
                .map(branchOfficeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BranchOfficeDTO> findByOrganizationId(Long organizationId) {
        List<BranchOffice> branchOffices = branchOfficeRepository.findByOrganizationId(organizationId);
        return branchOfficeMapper.toDto(branchOffices);
    }

    @Override
    @Transactional
    public void deleteBranchOffice(Long id) {
        if (!branchOfficeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Branch office not found with id: " + id);
        }
        branchOfficeRepository.deleteById(id);
    }
}
