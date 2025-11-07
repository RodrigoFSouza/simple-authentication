package br.com.cronos.simple_security.service;

import br.com.cronos.simple_security.domain.entity.Permission;
import br.com.cronos.simple_security.domain.entity.Role;
import br.com.cronos.simple_security.domain.entity.User;
import br.com.cronos.simple_security.repository.PermissionRepository;
import br.com.cronos.simple_security.repository.RoleRepository;
import br.com.cronos.simple_security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Transactional
    public User assignRoleToUser(Long userId, Long roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId));

        user.getRoles().add(role);
        return userRepository.save(user);
    }

    @Transactional
    public Role assignPermissionToRole(Long roleId, Long permissionId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId));

        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new RuntimeException("Permission not found with id: " + permissionId));

        role.getPermissions().add(permission);
        return roleRepository.save(role);
    }
}
