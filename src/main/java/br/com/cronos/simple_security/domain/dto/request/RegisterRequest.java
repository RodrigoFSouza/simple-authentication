package br.com.cronos.simple_security.domain.dto.request;

import java.util.Set;

public record RegisterRequest(String firstname, String lastname, String email, String password, Set<String> roles) {
}
