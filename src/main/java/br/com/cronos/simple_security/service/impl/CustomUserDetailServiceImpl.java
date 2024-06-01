package br.com.cronos.simple_security.service.impl;

import br.com.cronos.simple_security.domain.entity.User;
import br.com.cronos.simple_security.repository.UserRepository;
import br.com.cronos.simple_security.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class CustomUserDetailServiceImpl implements CustomUserDetailsService {

    private final UserRepository userRepository;

    public UserDetails mapDataForRoles(String email) {
        try {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
            return new UserDetails() {
                @Override
                public Collection<? extends GrantedAuthority> getAuthorities() {
                    return null; // you can later set the role and permission details for user.
                }

                @Override
                public String getPassword() {
                    return user.getPassword();
                }

                @Override
                public String getUsername() {
                    return user.getEmail();
                }

                @Override
                public boolean isAccountNonExpired() {
                    return user.isAccountNonExpired();
                }

                @Override
                public boolean isAccountNonLocked() {
                    return user.isAccountNonLocked();
                }

                @Override
                public boolean isCredentialsNonExpired() {
                    return user.isCredentialsNonExpired();
                }

                @Override
                public boolean isEnabled() {
                    return user.isEnabled();
                }
            };
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