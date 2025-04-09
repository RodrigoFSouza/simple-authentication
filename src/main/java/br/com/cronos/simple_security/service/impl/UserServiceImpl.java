package br.com.cronos.simple_security.service.impl;

import br.com.cronos.simple_security.domain.dto.request.UserCreateRequestDTO;
import br.com.cronos.simple_security.domain.dto.UserDTO;
import br.com.cronos.simple_security.domain.dto.request.UserUpdateRequestDTO;
import br.com.cronos.simple_security.domain.dto.request.AuthenticationRequest;
import br.com.cronos.simple_security.domain.dto.request.RegisterRequest;
import br.com.cronos.simple_security.domain.dto.response.AuthenticationResponse;
import br.com.cronos.simple_security.domain.dto.response.UserCreatedResponse;
import br.com.cronos.simple_security.domain.entity.User;
import br.com.cronos.simple_security.exception.EmailAlreadyExistsException;
import br.com.cronos.simple_security.exception.ResourceNotFoundException;
import br.com.cronos.simple_security.mapper.UserMapper;
import br.com.cronos.simple_security.repository.UserRepository;
import br.com.cronos.simple_security.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserCreatedResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException("Email already registered: " + request.email());
        }
        String encodedPassword = passwordEncoder.encode(request.password());
        var user = User.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .password(encodedPassword)
                .isEnabled(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isAccountNonExpired(true)
                .build();
        var savedUser = userRepository.save(user);

        return new UserCreatedResponse(savedUser.getId());
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = (User) authentication.getPrincipal();
            return new AuthenticationResponse("Successfully Authenticated. Welcome " + user.getFirstname());
        } catch (Exception ex) {
            throw new UsernameNotFoundException("Authentication failed: Invalid credentials");
        }
    }

    @Override
    public Page<UserDTO> findAllUsers(Specification<User> spec, Pageable pageable) {
        return userRepository.findAll(spec, pageable).map(userMapper::toDto);
    }

    @Override
    public UserDTO findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public UserDTO createUser(UserCreateRequestDTO userCreateRequestDTO) {
        if (userRepository.existsByEmail(userCreateRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email already registered: " + userCreateRequestDTO.getEmail());
        }

        User user = new User();
        user.setFirstname(userCreateRequestDTO.getFirstname());
        user.setLastname(userCreateRequestDTO.getLastname());
        user.setEmail(userCreateRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userCreateRequestDTO.getPassword()));
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);

        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    @Transactional
    public UserDTO updateUser(Long id, UserUpdateRequestDTO userUpdateRequestDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if (StringUtils.hasText(userUpdateRequestDTO.getFirstname())) {
            user.setFirstname(userUpdateRequestDTO.getFirstname());
        }
        if (StringUtils.hasText(userUpdateRequestDTO.getLastname())) {
            user.setLastname(userUpdateRequestDTO.getLastname());
        }
        if (StringUtils.hasText(userUpdateRequestDTO.getEmail()) && !Objects.equals(user.getEmail(), userUpdateRequestDTO.getEmail())) {
            if (userRepository.existsByEmail(userUpdateRequestDTO.getEmail())) {
                throw new EmailAlreadyExistsException("Email already registered: " + userUpdateRequestDTO.getEmail());
            }
            user.setEmail(userUpdateRequestDTO.getEmail());
        }
        if (userUpdateRequestDTO.getIsAccountNonExpired() != null) {
            user.setAccountNonExpired(userUpdateRequestDTO.getIsAccountNonExpired());
        }
        if (userUpdateRequestDTO.getIsAccountNonLocked() != null) {
            user.setAccountNonLocked(userUpdateRequestDTO.getIsAccountNonLocked());
        }
        if (userUpdateRequestDTO.getIsCredentialsNonExpired() != null) {
            user.setCredentialsNonExpired(userUpdateRequestDTO.getIsCredentialsNonExpired());
        }
        if (userUpdateRequestDTO.getIsEnabled() != null) {
            user.setEnabled(userUpdateRequestDTO.getIsEnabled());
        }

        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}