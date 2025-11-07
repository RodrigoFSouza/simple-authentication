package br.com.cronos.simple_security.service.impl;

import br.com.cronos.simple_security.domain.dto.TeamDTO;
import br.com.cronos.simple_security.domain.dto.request.TeamCreateRequestDTO;
import br.com.cronos.simple_security.domain.dto.request.TeamUpdateRequestDTO;
import br.com.cronos.simple_security.domain.entity.Organization;
import br.com.cronos.simple_security.domain.entity.Team;
import br.com.cronos.simple_security.domain.entity.User;
import br.com.cronos.simple_security.exception.ResourceNotFoundException;
import br.com.cronos.simple_security.mapper.TeamMapper;
import br.com.cronos.simple_security.repository.OrganizationRepository;
import br.com.cronos.simple_security.repository.TeamRepository;
import br.com.cronos.simple_security.repository.UserRepository;
import br.com.cronos.simple_security.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final TeamMapper teamMapper;

    @Override
    @Transactional
    public TeamDTO createTeam(TeamCreateRequestDTO request) {
        Organization organization = organizationRepository.findById(request.getOrganizationId())
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found with id: " + request.getOrganizationId()));

        User leader = userRepository.findById(request.getLeaderId())
                .orElseThrow(() -> new ResourceNotFoundException("Leader not found with id: " + request.getLeaderId()));

        Team team = Team.builder()
                .name(request.getName())
                .description(request.getDescription())
                .organization(organization)
                .leader(leader)
                .members(new ArrayList<>())
                .build();

        // Add members if provided
        if (request.getMemberIds() != null && !request.getMemberIds().isEmpty()) {
            List<User> members = userRepository.findAllById(request.getMemberIds());
            team.setMembers(members);
        }

        Team saved = teamRepository.save(team);
        return teamMapper.toDto(saved);
    }

    @Override
    @Transactional
    public TeamDTO updateTeam(Long id, TeamUpdateRequestDTO request) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found with id: " + id));

        if (request.getName() != null) {
            team.setName(request.getName());
        }
        if (request.getDescription() != null) {
            team.setDescription(request.getDescription());
        }
        if (request.getLeaderId() != null) {
            User leader = userRepository.findById(request.getLeaderId())
                    .orElseThrow(() -> new ResourceNotFoundException("Leader not found with id: " + request.getLeaderId()));
            team.setLeader(leader);
        }
        if (request.getMemberIds() != null) {
            List<User> members = userRepository.findAllById(request.getMemberIds());
            team.setMembers(members);
        }

        Team updated = teamRepository.save(team);
        return teamMapper.toDto(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public TeamDTO findById(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found with id: " + id));
        return teamMapper.toDto(team);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TeamDTO> findAll(Pageable pageable) {
        return teamRepository.findAll(pageable)
                .map(teamMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeamDTO> findByOrganizationId(Long organizationId) {
        List<Team> teams = teamRepository.findByOrganizationId(organizationId);
        return teamMapper.toDto(teams);
    }

    @Override
    @Transactional
    public void deleteTeam(Long id) {
        if (!teamRepository.existsById(id)) {
            throw new ResourceNotFoundException("Team not found with id: " + id);
        }
        teamRepository.deleteById(id);
    }

    @Override
    @Transactional
    public TeamDTO addMemberToTeam(Long teamId, Long userId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found with id: " + teamId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        if (!team.getMembers().contains(user)) {
            team.getMembers().add(user);
            team = teamRepository.save(team);
        }

        return teamMapper.toDto(team);
    }

    @Override
    @Transactional
    public TeamDTO removeMemberFromTeam(Long teamId, Long userId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found with id: " + teamId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        team.getMembers().remove(user);
        team = teamRepository.save(team);

        return teamMapper.toDto(team);
    }
}
