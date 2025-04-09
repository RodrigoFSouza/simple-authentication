package br.com.cronos.simple_security.service.impl;

import br.com.cronos.simple_security.domain.entity.PasswordResetToken;
import br.com.cronos.simple_security.domain.entity.User;
import br.com.cronos.simple_security.domain.repository.PasswordResetTokenRepository;
import br.com.cronos.simple_security.exception.ResourceNotFoundException;
import br.com.cronos.simple_security.exception.TokenExpiredException;
import br.com.cronos.simple_security.repository.UserRepository;
import br.com.cronos.simple_security.service.PasswordResetService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Value("${app.password-reset.url}") // You'll need to define this property
    private String passwordResetUrlBase;

    @Override
    @Transactional
    public void requestPasswordReset(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        // Invalidate previous tokens for this user
        tokenRepository.deleteByUser_Id(user.getId());

        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken(token, user);
        tokenRepository.save(resetToken);

        sendPasswordResetEmail(user.getEmail(), token);
    }

    @Override
    @Transactional
    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = validateAndGetToken(token);
        User user = resetToken.getUser();

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // Invalidate the token after use
        tokenRepository.delete(resetToken);
    }

    @Override
    public void validateToken(String token) {
        validateAndGetToken(token); // Just need to check if validation passes
    }

    private PasswordResetToken validateAndGetToken(String token) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid password reset token"));

        if (resetToken.isExpired()) {
            tokenRepository.delete(resetToken); // Clean up expired token
            throw new TokenExpiredException("Password reset token has expired");
        }
        return resetToken;
    }

    private void sendPasswordResetEmail(String recipientEmail, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailFrom);
        message.setTo(recipientEmail);
        message.setSubject("Password Reset Request");
        // Consider creating a more user-friendly frontend URL
        String resetUrl = passwordResetUrlBase + "?token=" + token;
        message.setText("To reset your password, click the link below:\n" + resetUrl
                + "\n\nIf you did not request a password reset, please ignore this email.");
        mailSender.send(message);
    }
} 