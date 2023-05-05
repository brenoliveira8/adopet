package br.com.mascarenhasb2.adopet.domain.model.pet.dto;

import br.com.mascarenhasb2.adopet.domain.model.pet.PetSize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PetCreationDTO(
        @NotBlank
        String name,
        @NotBlank
        String age,
        @NotNull
        PetSize size,
        @NotBlank
        String behavior,
        @NotBlank
        String photo,
        @NotNull
        Long shelterId
){
}
