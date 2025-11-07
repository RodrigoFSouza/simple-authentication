package br.com.cronos.simple_security.service.impl;

import br.com.cronos.simple_security.domain.entity.Permission;
import br.com.cronos.simple_security.domain.entity.Role;
import br.com.cronos.simple_security.domain.entity.User;
import br.com.cronos.simple_security.repository.UserRepository;
import br.com.cronos.simple_security.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomUserDetailServiceImpl implements CustomUserDetailsService {

    private final UserRepository userRepository;

    public UserDetails mapDataForRoles(String email) {
        try {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

            // Load authorities (roles + permissions)
            Set<GrantedAuthority> authorities = new HashSet<>();

            // Add roles to authorities
            for (Role role : user.getRoles()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));

                // Add permissions from each role to authorities
                for (Permission permission : role.getPermissions()) {
                    authorities.add(new SimpleGrantedAuthority(permission.getName()));
                }
            }

            return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.isEnabled(),
                user.isAccountNonExpired(),
                user.isCredentialsNonExpired(),
                user.isAccountNonLocked(),
                authorities
            );
        } catch (Exception ex) {
            throw new UsernameNotFoundException(ex.getMessage());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = mapDataForRoles(username);
        return user;
    }
}