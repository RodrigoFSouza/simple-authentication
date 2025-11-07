package br.com.cronos.simple_security.service;

import br.com.cronos.simple_security.domain.dto.TeamDTO;
import br.com.cronos.simple_security.domain.dto.request.TeamCreateRequestDTO;
import br.com.cronos.simple_security.domain.dto.request.TeamUpdateRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TeamService {

    TeamDTO createTeam(TeamCreateRequestDTO request);

    TeamDTO updateTeam(Long id, TeamUpdateRequestDTO request);

    TeamDTO findById(Long id);

    Page<TeamDTO> findAll(Pageable pageable);

    List<TeamDTO> findByOrganizationId(Long organizationId);

    void deleteTeam(Long id);

    TeamDTO addMemberToTeam(Long teamId, Long userId);

    TeamDTO removeMemberFromTeam(Long teamId, Long userId);
}
