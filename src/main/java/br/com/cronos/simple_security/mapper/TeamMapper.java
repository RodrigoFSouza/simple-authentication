package br.com.cronos.simple_security.mapper;

import br.com.cronos.simple_security.domain.dto.TeamDTO;
import br.com.cronos.simple_security.domain.entity.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TeamMapper {

    private final UserMapper userMapper;

    public TeamDTO toDto(Team team) {
        if (team == null) {
            return null;
        }
        return TeamDTO.builder()
                .id(team.getId())
                .name(team.getName())
                .description(team.getDescription())
                .organizationId(team.getOrganization() != null ? team.getOrganization().getId() : null)
                .organizationName(team.getOrganization() != null ? team.getOrganization().getName() : null)
                .leaderId(team.getLeader() != null ? team.getLeader().getId() : null)
                .leaderName(team.getLeader() != null ?
                        team.getLeader().getFirstname() + " " + team.getLeader().getLastname() : null)
                .members(team.getMembers() != null ? userMapper.toDto(team.getMembers()) : null)
                .memberCount(team.getMembers() != null ? team.getMembers().size() : 0)
                .createdAt(team.getCreatedAt())
                .updatedAt(team.getUpdatedAt())
                .build();
    }

    public List<TeamDTO> toDto(List<Team> teams) {
        return teams.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
