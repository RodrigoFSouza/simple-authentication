package br.com.cronos.simple_security.api;

import br.com.cronos.simple_security.domain.dto.request.AuthenticationRequest;
import br.com.cronos.simple_security.domain.dto.request.RegisterRequest;
import br.com.cronos.simple_security.domain.dto.response.AuthenticationResponse;
import br.com.cronos.simple_security.domain.dto.response.UserCreatedResponse;
import br.com.cronos.simple_security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<UserCreatedResponse> register(@RequestBody RegisterRequest request) {
        var authenticationResponse = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationResponse);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(userService.authenticate(request));
    }
}
