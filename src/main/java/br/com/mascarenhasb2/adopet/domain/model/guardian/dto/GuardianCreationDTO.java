package br.com.mascarenhasb2.adopet.domain.model.guardian.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record GuardianCreationDTO(
        @NotBlank
        String name,
        @NotBlank
        String phone,
        @NotBlank
        String city,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String password,
        String about,
        String photo
) {
}
