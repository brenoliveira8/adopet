package br.com.mascarenhasb2.adopet.domain.model.shelter.dto;

import br.com.mascarenhasb2.adopet.domain.model.address.dto.AddressUpdateDTO;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotNull;

public record ShelterUpdateDTO(
        @NotNull
        Long id,
        String name,
        String phone,
        @Embedded
        AddressUpdateDTO address
) {
}
