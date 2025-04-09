package br.com.cronos.simple_security.service;

public interface PasswordResetService {
    void requestPasswordReset(String email);
    void resetPassword(String token, String newPassword);
    void validateToken(String token);
} 