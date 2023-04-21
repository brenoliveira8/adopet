package br.com.mascarenhasb2.adopet.util;

import br.com.mascarenhasb2.adopet.domain.repository.UserRepository;
import br.com.mascarenhasb2.adopet.infra.exception.EmailConflictException;

public class EmailUtil {
    private EmailUtil(){
        throw new IllegalStateException("Utility Class!");
    }
    public static void verifyIfExists(UserRepository repository, String email){
        if (Boolean.TRUE.equals(repository.existsByEmail(email))){
            throw new EmailConflictException("E-mail já cadastrado no sistema!");
        }
    }
}
