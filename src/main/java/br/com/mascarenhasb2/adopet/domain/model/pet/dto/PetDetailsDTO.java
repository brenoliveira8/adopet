package br.com.mascarenhasb2.adopet.domain.model.pet.dto;

import br.com.mascarenhasb2.adopet.domain.model.ReturnInformationDTO;
import br.com.mascarenhasb2.adopet.domain.model.pet.Pet;
import br.com.mascarenhasb2.adopet.domain.model.pet.PetSize;

public record PetDetailsDTO(
        Long id,
        Long shelterId,
        String name,
        PetSize size,
        String behavior,
        Boolean adopted,
        String age,
        String photo
) implements ReturnInformationDTO{
    public PetDetailsDTO(Pet pet){
        this(pet.getId(), pet.getShelter().getId(), pet.getName(), pet.getSize(), pet.getBehavior(), pet.getAdopted(), pet.getAge(), pet.getPhoto());
    }
}
