package br.com.mascarenhasb2.adopet.domain.model.adoption.dto;

import jakarta.validation.constraints.NotNull;

public record AdoptionCreationDTO(
        @NotNull
        Long guardianId,
        @NotNull
        Long petId
){
}
