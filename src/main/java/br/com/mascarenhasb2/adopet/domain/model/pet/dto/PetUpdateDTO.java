package br.com.mascarenhasb2.adopet.domain.model.pet.dto;

import br.com.mascarenhasb2.adopet.domain.model.pet.PetSize;
import jakarta.validation.constraints.NotNull;

public record PetUpdateDTO(
        @NotNull
        Long id,
        Long shelterId,
        String name,
        PetSize size,
        String behavior,
        Boolean adopted,
        String age,
        String photo
) {
}
