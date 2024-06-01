package br.com.cronos.simple_security.service.impl;

import br.com.cronos.simple_security.domain.dto.request.AuthenticationRequest;
import br.com.cronos.simple_security.domain.dto.request.RegisterRequest;
import br.com.cronos.simple_security.domain.dto.response.AuthenticationResponse;
import br.com.cronos.simple_security.domain.dto.response.UserCreatedResponse;
import br.com.cronos.simple_security.domain.entity.User;
import br.com.cronos.simple_security.repository.UserRepository;
import br.com.cronos.simple_security.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public UserCreatedResponse register(RegisterRequest request) {
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

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //saveUserToken(user, jwtToken);
            return new AuthenticationResponse("Successfully Authenticate"); // you can return accessToken here.
        } catch (Exception ex) {
            throw new UsernameNotFoundException("Authentication failed " + ex.getMessage());
        }
    }
}