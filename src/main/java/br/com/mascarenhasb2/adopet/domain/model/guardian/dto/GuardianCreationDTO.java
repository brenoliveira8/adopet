package br.com.mascarenhasb2.adopet.domain.model.guardian.dto;

import br.com.mascarenhasb2.adopet.domain.model.user.dto.UserDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record GuardianCreationDTO(
        @NotBlank
        String name,
        @NotBlank
        String phone,
        @NotBlank
        String city,
        @Valid
        UserDTO user,
        String about,
        String photo
) {
}
