package br.com.cronos.simple_security.domain.dto.request;

public record AuthenticationRequest(String email, String password) {
}
