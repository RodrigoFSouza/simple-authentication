package br.com.cronos.simple_security.service;

import br.com.cronos.simple_security.domain.dto.request.AuthenticationRequest;
import br.com.cronos.simple_security.domain.dto.request.RegisterRequest;
import br.com.cronos.simple_security.domain.dto.response.AuthenticationResponse;
import br.com.cronos.simple_security.domain.dto.response.UserCreatedResponse;

public interface UserService {
    UserCreatedResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
