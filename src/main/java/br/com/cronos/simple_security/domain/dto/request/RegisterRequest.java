package br.com.cronos.simple_security.domain.dto.request;

public record RegisterRequest(String firstname, String lastname, String email, String password) {
}
