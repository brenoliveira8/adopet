package br.com.mascarenhasb2.adopet.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {
    @ExceptionHandler (EmailConflictException.class)
    public ResponseEntity<ErrorDTO> handleEmailConflictException(EmailConflictException exception) {
        var error = new ErrorDTO(String.valueOf(HttpStatus.CONFLICT.value()), exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }

    @ExceptionHandler (PetAlreadyAdoptedException.class)
    public ResponseEntity<ErrorDTO> handlePetAlreadyAdoptedException(PetAlreadyAdoptedException exception) {
        var error = new ErrorDTO(String.valueOf(HttpStatus.CONFLICT.value()), exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }

    @ExceptionHandler (EntityNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleEntityNotFoundException() {
        return ResponseEntity
                .notFound()
                .build();
    }

    @ExceptionHandler (ValidationException.class)
    public ResponseEntity<ErrorDTO> handleValidationException() {
        var error = new ErrorDTO(String.valueOf(HttpStatus.UNAUTHORIZED.value()), "Login ou senha inválidos!");
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(error);
    }

    @ExceptionHandler ({AuthenticationException.class, InsufficientAuthenticationException.class})
    public ResponseEntity<ErrorDTO> handleAuthenticationException() {
        var error = new ErrorDTO(String.valueOf(HttpStatus.UNAUTHORIZED.value()), "Você não possui autorização para essa operação!");
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(error);
    }

    @ExceptionHandler (InvalidJwtTokenException.class)
    public ResponseEntity<ErrorDTO> handleInvalidJwtException(InvalidJwtTokenException exception) {
        var error = new ErrorDTO(String.valueOf(HttpStatus.UNAUTHORIZED.value()), exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(error);
    }

    @ExceptionHandler (MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDTO> handleInvalidArgumentTypeException(MethodArgumentTypeMismatchException exception) {
        var error = new ErrorDTO(String.valueOf(
                HttpStatus.BAD_REQUEST.value()),
                exception.getPropertyName() + " passed is invalid.");
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler (Exception.class)
    public ResponseEntity<ErrorDTO> handleInternalServerException(Exception exception) {
        var error = new ErrorDTO(String.valueOf(
                HttpStatus.INTERNAL_SERVER_ERROR.value()),
                exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }

    @ExceptionHandler (MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleArgumentNotValidException(MethodArgumentNotValidException exception) {
        var error = new ErrorDTO(String.valueOf(
                HttpStatus.BAD_REQUEST.value()),
                exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }
}
