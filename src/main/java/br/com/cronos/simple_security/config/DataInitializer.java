package br.com.cronos.simple_security.config;

import br.com.cronos.simple_security.domain.entity.Permission;
import br.com.cronos.simple_security.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final PermissionRepository permissionRepository;

    @Override
    public void run(String... args) {
        initializePermissions();
    }

    private void initializePermissions() {
        log.info("Initializing default permissions...");

        createPermissionIfNotExists("CREATE_ORGANIZATION");
        createPermissionIfNotExists("VIEW_ORGANIZATION");
        createPermissionIfNotExists("DELETE_ORGANIZATION");

        log.info("Default permissions initialized successfully.");
    }

    private void createPermissionIfNotExists(String permissionName) {
        if (permissionRepository.findByName(permissionName).isEmpty()) {
            Permission permission = Permission.builder()
                    .name(permissionName)
                    .build();
            permissionRepository.save(permission);
            log.info("Created permission: {}", permissionName);
        } else {
            log.info("Permission already exists: {}", permissionName);
        }
    }
}
