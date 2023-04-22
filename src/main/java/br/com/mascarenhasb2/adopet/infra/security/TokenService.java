package br.com.mascarenhasb2.adopet.infra.security;

import br.com.mascarenhasb2.adopet.domain.model.user.User;
import br.com.mascarenhasb2.adopet.infra.exception.InvalidJwtToken;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value ("${spring.api.security.token.secret}")
    private String secret;

    public String generateToken(User user) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("Adopet API")
                    .withSubject(user.getEmail())
                    .withExpiresAt(expireDate())
                    .withClaim("role", String.valueOf(user.getRole()))
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new JWTCreationException("Erro gerando o Token", exception);
        }
    }

    public String getSubject(String jwtToken) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("Adopet API")
                    .build()
                    .verify(jwtToken)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new InvalidJwtToken("Token JWT inválido.");
        }
    }
    private Instant expireDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
