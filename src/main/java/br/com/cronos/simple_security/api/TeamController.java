package br.com.cronos.simple_security.api;

import br.com.cronos.simple_security.domain.dto.TeamDTO;
import br.com.cronos.simple_security.domain.dto.request.TeamCreateRequestDTO;
import br.com.cronos.simple_security.domain.dto.request.TeamUpdateRequestDTO;
import br.com.cronos.simple_security.service.TeamService;
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
@RequestMapping("/api/teams")
@RequiredArgsConstructor
@Tag(name = "Teams", description = "Team management endpoints")
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    @Operation(summary = "Create a new team")
    public ResponseEntity<TeamDTO> createTeam(@Valid @RequestBody TeamCreateRequestDTO request) {
        TeamDTO created = teamService.createTeam(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a team")
    public ResponseEntity<TeamDTO> updateTeam(
            @PathVariable Long id,
            @Valid @RequestBody TeamUpdateRequestDTO request) {
        TeamDTO updated = teamService.updateTeam(id, request);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get team by ID")
    public ResponseEntity<TeamDTO> getTeamById(@PathVariable Long id) {
        TeamDTO team = teamService.findById(id);
        return ResponseEntity.ok(team);
    }

    @GetMapping
    @Operation(summary = "Get all teams with pagination")
    public ResponseEntity<Page<TeamDTO>> getAllTeams(Pageable pageable) {
        Page<TeamDTO> teams = teamService.findAll(pageable);
        return ResponseEntity.ok(teams);
    }

    @GetMapping("/organization/{organizationId}")
    @Operation(summary = "Get teams by organization ID")
    public ResponseEntity<List<TeamDTO>> getTeamsByOrganization(@PathVariable Long organizationId) {
        List<TeamDTO> teams = teamService.findByOrganizationId(organizationId);
        return ResponseEntity.ok(teams);
    }

    @PostMapping("/{teamId}/members/{userId}")
    @Operation(summary = "Add member to team")
    public ResponseEntity<TeamDTO> addMemberToTeam(
            @PathVariable Long teamId,
            @PathVariable Long userId) {
        TeamDTO team = teamService.addMemberToTeam(teamId, userId);
        return ResponseEntity.ok(team);
    }

    @DeleteMapping("/{teamId}/members/{userId}")
    @Operation(summary = "Remove member from team")
    public ResponseEntity<TeamDTO> removeMemberFromTeam(
            @PathVariable Long teamId,
            @PathVariable Long userId) {
        TeamDTO team = teamService.removeMemberFromTeam(teamId, userId);
        return ResponseEntity.ok(team);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a team")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }
}
