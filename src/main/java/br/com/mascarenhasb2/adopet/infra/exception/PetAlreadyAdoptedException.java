package br.com.mascarenhasb2.adopet.infra.exception;

public class PetAlreadyAdoptedException extends RuntimeException {
    public PetAlreadyAdoptedException(String message){
        super(message);
    }
}
