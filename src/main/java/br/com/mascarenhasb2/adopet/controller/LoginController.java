package br.com.mascarenhasb2.adopet.controller;

import br.com.mascarenhasb2.adopet.domain.model.user.User;
import br.com.mascarenhasb2.adopet.domain.model.user.dto.JwtTokenDTO;
import br.com.mascarenhasb2.adopet.domain.model.user.dto.UserDTO;
import br.com.mascarenhasb2.adopet.infra.security.TokenService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class LoginController {
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<JwtTokenDTO> login(@RequestBody @Valid UserDTO userDTO){
        var authenticationToken = new UsernamePasswordAuthenticationToken(userDTO.email(), userDTO.password());
        try {
            var authentication = manager.authenticate(authenticationToken);
            var jwtToken = tokenService.generateToken((User) authentication.getPrincipal());

            return ResponseEntity.ok(new JwtTokenDTO(jwtToken));
        } catch (AuthenticationException exception){
            throw new ValidationException();
        }
    }
}
