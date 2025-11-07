package br.com.cronos.simple_security.api;

import br.com.cronos.simple_security.domain.dto.BranchOfficeDTO;
import br.com.cronos.simple_security.domain.dto.request.BranchOfficeCreateRequestDTO;
import br.com.cronos.simple_security.domain.dto.request.BranchOfficeUpdateRequestDTO;
import br.com.cronos.simple_security.service.BranchOfficeService;
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
@RequestMapping("/api/branch-offices")
@RequiredArgsConstructor
@Tag(name = "Branch Offices", description = "Branch office management endpoints")
public class BranchOfficeController {

    private final BranchOfficeService branchOfficeService;

    @PostMapping
    @Operation(summary = "Create a new branch office")
    public ResponseEntity<BranchOfficeDTO> createBranchOffice(@Valid @RequestBody BranchOfficeCreateRequestDTO request) {
        BranchOfficeDTO created = branchOfficeService.createBranchOffice(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a branch office")
    public ResponseEntity<BranchOfficeDTO> updateBranchOffice(
            @PathVariable Long id,
            @Valid @RequestBody BranchOfficeUpdateRequestDTO request) {
        BranchOfficeDTO updated = branchOfficeService.updateBranchOffice(id, request);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get branch office by ID")
    public ResponseEntity<BranchOfficeDTO> getBranchOfficeById(@PathVariable Long id) {
        BranchOfficeDTO branchOffice = branchOfficeService.findById(id);
        return ResponseEntity.ok(branchOffice);
    }

    @GetMapping
    @Operation(summary = "Get all branch offices with pagination")
    public ResponseEntity<Page<BranchOfficeDTO>> getAllBranchOffices(Pageable pageable) {
        Page<BranchOfficeDTO> branchOffices = branchOfficeService.findAll(pageable);
        return ResponseEntity.ok(branchOffices);
    }

    @GetMapping("/organization/{organizationId}")
    @Operation(summary = "Get branch offices by organization ID")
    public ResponseEntity<List<BranchOfficeDTO>> getBranchOfficesByOrganization(@PathVariable Long organizationId) {
        List<BranchOfficeDTO> branchOffices = branchOfficeService.findByOrganizationId(organizationId);
        return ResponseEntity.ok(branchOffices);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a branch office")
    public ResponseEntity<Void> deleteBranchOffice(@PathVariable Long id) {
        branchOfficeService.deleteBranchOffice(id);
        return ResponseEntity.noContent().build();
    }
}
