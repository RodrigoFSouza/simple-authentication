package br.com.cronos.simple_security.mapper;

import br.com.cronos.simple_security.domain.dto.UserDTO;
import br.com.cronos.simple_security.domain.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component // Or define as static methods if preferred
public class UserMapper {

    public UserDTO toDto(User user) {
        if (user == null) {
            return null;
        }
        return UserDTO.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .isAccountNonExpired(user.isAccountNonExpired())
                .isAccountNonLocked(user.isAccountNonLocked())
                .isCredentialsNonExpired(user.isCredentialsNonExpired())
                .isEnabled(user.isEnabled())
                .build();
    }

    public List<UserDTO> toDto(List<User> users) {
        return users.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // Add methods for mapping UserCreateDTO/UserUpdateDTO to User if needed
    // Be careful with password handling - usually done in the service
} 