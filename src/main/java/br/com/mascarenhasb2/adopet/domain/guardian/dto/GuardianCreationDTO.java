package br.com.mascarenhasb2.adopet.domain.guardian.dto;

import jakarta.validation.constraints.NotBlank;

public record GuardianCreationDTO(
        @NotBlank
        String name,
        @NotBlank
        String phone,
        @NotBlank
        String city,
        String about,
        String photo
) {
}
