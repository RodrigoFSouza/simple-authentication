package br.com.cronos.simple_security.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamUpdateRequestDTO {

    private String name;
    private String description;
    private Long leaderId;
    private List<Long> memberIds;
}
