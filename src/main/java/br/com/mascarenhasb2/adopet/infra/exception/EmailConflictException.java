package br.com.mascarenhasb2.adopet.infra.exception;

public class EmailConflictException extends RuntimeException{
    public EmailConflictException(String message){
        super(message);
    }
}
