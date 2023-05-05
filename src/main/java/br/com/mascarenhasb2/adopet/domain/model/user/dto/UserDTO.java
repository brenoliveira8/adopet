package br.com.mascarenhasb2.adopet.domain.model.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDTO(
        @NotBlank
        @Email
        String email,
        @NotBlank
        String password
){
}
