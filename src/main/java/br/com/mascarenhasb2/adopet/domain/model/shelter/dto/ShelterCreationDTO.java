package br.com.mascarenhasb2.adopet.domain.model.shelter.dto;

import br.com.mascarenhasb2.adopet.domain.model.address.dto.AddressDTO;
import br.com.mascarenhasb2.adopet.domain.model.user.dto.UserDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record ShelterCreationDTO(
        @NotBlank
        String name,
        @NotBlank
        String phone,
        @Valid
        AddressDTO address,
        @Valid
        UserDTO user
) {
}
