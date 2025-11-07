package br.com.cronos.simple_security.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BranchOfficeDTO {

    private Long id;
    private String name;
    private Long organizationId;
    private String organizationName;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String phone;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
