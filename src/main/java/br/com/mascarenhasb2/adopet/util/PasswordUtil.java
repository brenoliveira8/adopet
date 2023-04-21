package br.com.mascarenhasb2.adopet.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {
    private PasswordUtil(){
        throw new IllegalStateException("Utility Class!");
    }
    public static String encode(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}
