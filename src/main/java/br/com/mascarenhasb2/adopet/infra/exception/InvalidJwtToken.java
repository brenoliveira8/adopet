package br.com.mascarenhasb2.adopet.infra.exception;

public class InvalidJwtToken extends RuntimeException {
    public InvalidJwtToken(String message) {
        super(message);
    }
}
