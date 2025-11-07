package br.com.cronos.simple_security.exception;

public class InvalidCnpjException extends RuntimeException {
    public InvalidCnpjException(String message) {
        super(message);
    }
}
