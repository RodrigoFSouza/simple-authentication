package br.com.cronos.simple_security.exception;

public class MaxUsersExceededException extends RuntimeException {
    public MaxUsersExceededException(String message) {
        super(message);
    }
}
