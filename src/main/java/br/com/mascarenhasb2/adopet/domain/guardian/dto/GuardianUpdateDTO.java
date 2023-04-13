package br.com.mascarenhasb2.adopet.domain.guardian.dto;

import jakarta.validation.constraints.NotNull;

public record GuardianUpdateDTO(
        @NotNull
        Long id,
        String name,
        String phone,
        String city,
        String about,
        String photo) {
}
