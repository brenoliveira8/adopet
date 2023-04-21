package br.com.mascarenhasb2.adopet.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandling {
    @ExceptionHandler (EmailConflictException.class)
    public ResponseEntity<ErrorDTO> handleEmailConflict(EmailConflictException exception){
        var error = new ErrorDTO(String.valueOf(HttpStatus.CONFLICT.value()), exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }


}
