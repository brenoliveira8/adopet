package br.com.mascarenhasb2.adopet.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ExceptionHandling {
    @ExceptionHandler (EmailConflictException.class)
    public ResponseEntity<ErrorDTO> handleEmailConflict(EmailConflictException exception){
        var error = new ErrorDTO(String.valueOf(HttpStatus.CONFLICT.value()), exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }

    @ExceptionHandler(PetAlreadyAdoptedException.class)
    public ResponseEntity<ErrorDTO> handlePetAlreadyAdopted(PetAlreadyAdoptedException exception){
        var error = new ErrorDTO(String.valueOf(HttpStatus.CONFLICT.value()), exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleEntityNotFound(){
        return ResponseEntity
                .notFound()
                .build();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDTO> handleInvalidArgumentType(MethodArgumentTypeMismatchException exception){
        var error = new ErrorDTO(String.valueOf(
                HttpStatus.BAD_REQUEST.value()),
                exception.getPropertyName() + " passed is invalid.");
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }
}
