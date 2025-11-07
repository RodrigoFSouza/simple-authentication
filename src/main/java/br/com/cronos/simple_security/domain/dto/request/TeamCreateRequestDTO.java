package br.com.cronos.simple_security.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamCreateRequestDTO {

    @NotBlank(message = "Team name is required")
    private String name;

    private String description;

    @NotNull(message = "Organization ID is required")
    private Long organizationId;

    @NotNull(message = "Leader ID is required")
    private Long leaderId;

    private List<Long> memberIds;
}
