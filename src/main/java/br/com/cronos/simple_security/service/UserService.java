package br.com.cronos.simple_security.service;

import br.com.cronos.simple_security.domain.dto.request.AuthenticationRequest;
import br.com.cronos.simple_security.domain.dto.request.RegisterRequest;
import br.com.cronos.simple_security.domain.dto.response.AuthenticationResponse;
import br.com.cronos.simple_security.domain.dto.response.UserCreatedResponse;
import br.com.cronos.simple_security.domain.dto.request.UserCreateRequestDTO;
import br.com.cronos.simple_security.domain.dto.UserDTO;
import br.com.cronos.simple_security.domain.dto.request.UserUpdateRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import br.com.cronos.simple_security.domain.entity.User;

public interface UserService {
    UserCreatedResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
    Page<UserDTO> findAllUsers(Specification<User> spec, Pageable pageable);
    UserDTO findUserById(Long id);
    UserDTO createUser(UserCreateRequestDTO userCreateRequestDTO);
    UserDTO updateUser(Long id, UserUpdateRequestDTO userUpdateRequestDTO);
    void deleteUser(Long id);
}
