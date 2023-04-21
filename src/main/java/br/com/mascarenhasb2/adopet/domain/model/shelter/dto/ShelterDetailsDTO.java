package br.com.mascarenhasb2.adopet.domain.model.shelter.dto;

import br.com.mascarenhasb2.adopet.domain.model.ReturnInformationDTO;
import br.com.mascarenhasb2.adopet.domain.model.address.Address;
import br.com.mascarenhasb2.adopet.domain.model.shelter.Shelter;

public record ShelterDetailsDTO(
        Long id,
        String name,
        String phone,
        Address address
) implements ReturnInformationDTO {
    public ShelterDetailsDTO(Shelter shelter){
        this(shelter.getId(), shelter.getName(), shelter.getPhone(), shelter.getAddress());
    }
}
