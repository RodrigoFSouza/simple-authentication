package br.com.cronos.simple_security.api;

import br.com.cronos.simple_security.domain.dto.PasswordResetDTO;
import br.com.cronos.simple_security.domain.dto.request.PasswordResetRequestDTO;
import br.com.cronos.simple_security.service.PasswordResetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/password-reset")
@RequiredArgsConstructor
@Tag(name = "Password Reset", description = "APIs for password reset functionality")
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    @Operation(summary = "Request password reset", description = "Request a password reset token to be sent to the user's email")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Password reset email sent successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping("/request")
    public ResponseEntity<Void> requestPasswordReset(
            @Parameter(description = "Password reset request data", required = true)
            @Valid @RequestBody PasswordResetRequestDTO requestDTO) {
        passwordResetService.requestPasswordReset(requestDTO.getEmail());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Reset password", description = "Reset password using the token received via email")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Password reset successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid token or password"),
        @ApiResponse(responseCode = "404", description = "Token not found")
    })
    @PostMapping("/reset")
    public ResponseEntity<Void> resetPassword(
            @Parameter(description = "Password reset data", required = true)
            @Valid @RequestBody PasswordResetDTO resetDTO) {
        passwordResetService.resetPassword(resetDTO.getToken(), resetDTO.getNewPassword());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Validate token", description = "Validate if a password reset token is valid")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Token is valid"),
        @ApiResponse(responseCode = "400", description = "Token is invalid or expired"),
        @ApiResponse(responseCode = "404", description = "Token not found")
    })
    @GetMapping("/validate")
    public ResponseEntity<Void> validateToken(
            @Parameter(description = "Password reset token", required = true)
            @RequestParam String token) {
        passwordResetService.validateToken(token);
        return ResponseEntity.ok().build();
    }
} 