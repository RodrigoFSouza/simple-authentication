package br.com.cronos.simple_security.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
@RequiredArgsConstructor
@Tag(name = "Healt Check", description = "APIs to health check of Api")
@SecurityRequirement(name = "bearerAuth")
public class HealthCheckController {

    @Operation(summary = "Health Check a system", description = "Request a check health of system")
    @GetMapping
    public String healthCheck () {
        return  "Simple Security UP" ;
    }
}
