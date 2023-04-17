package br.com.mascarenhasb2.adopet.domain.model.shelter.dto;

import br.com.mascarenhasb2.adopet.domain.model.address.dto.AddressDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record ShelterCreationDTO(
        @NotBlank
        String name,
        @NotBlank
        String phone,
        @Valid
        AddressDTO address
) {
}
