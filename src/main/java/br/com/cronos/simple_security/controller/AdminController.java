package br.com.cronos.simple_security.controller;

import br.com.cronos.simple_security.domain.entity.Role;
import br.com.cronos.simple_security.domain.entity.User;
import br.com.cronos.simple_security.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/users/{userId}/roles/{roleId}")
    public ResponseEntity<User> assignRole(
            @PathVariable Long userId,
            @PathVariable Long roleId) {
        User user = adminService.assignRoleToUser(userId, roleId);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/roles/{roleId}/permissions/{permissionId}")
    public ResponseEntity<Role> assignPermission(
            @PathVariable Long roleId,
            @PathVariable Long permissionId) {
        Role role = adminService.assignPermissionToRole(roleId, permissionId);
        return ResponseEntity.ok(role);
    }
}
