package br.com.cronos.simple_security.api;

import br.com.cronos.simple_security.domain.dto.OrganizationDTO;
import br.com.cronos.simple_security.domain.dto.request.OrganizationCreateRequestDTO;
import br.com.cronos.simple_security.domain.dto.request.OrganizationUpdateRequestDTO;
import br.com.cronos.simple_security.service.OrganizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organizations")
@RequiredArgsConstructor
@Tag(name = "Organizations", description = "Organization management endpoints")
public class OrganizationController {

    private final OrganizationService organizationService;

    @PostMapping
    @Operation(summary = "Create a new organization")
    public ResponseEntity<OrganizationDTO> createOrganization(@Valid @RequestBody OrganizationCreateRequestDTO request) {
        OrganizationDTO created = organizationService.createOrganization(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an organization")
    public ResponseEntity<OrganizationDTO> updateOrganization(
            @PathVariable Long id,
            @Valid @RequestBody OrganizationUpdateRequestDTO request) {
        OrganizationDTO updated = organizationService.updateOrganization(id, request);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get organization by ID")
    public ResponseEntity<OrganizationDTO> getOrganizationById(@PathVariable Long id) {
        OrganizationDTO organization = organizationService.findById(id);
        return ResponseEntity.ok(organization);
    }

    @GetMapping
    @Operation(summary = "Get all organizations with pagination")
    public ResponseEntity<Page<OrganizationDTO>> getAllOrganizations(Pageable pageable) {
        Page<OrganizationDTO> organizations = organizationService.findAll(pageable);
        return ResponseEntity.ok(organizations);
    }

    @GetMapping("/{parentId}/sub-organizations")
    @Operation(summary = "Get sub-organizations by parent organization ID")
    public ResponseEntity<List<OrganizationDTO>> getSubOrganizations(@PathVariable Long parentId) {
        List<OrganizationDTO> subOrganizations = organizationService.findSubOrganizations(parentId);
        return ResponseEntity.ok(subOrganizations);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an organization")
    public ResponseEntity<Void> deleteOrganization(@PathVariable Long id) {
        organizationService.deleteOrganization(id);
        return ResponseEntity.noContent().build();
    }
}
