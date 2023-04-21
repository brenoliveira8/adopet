package br.com.mascarenhasb2.adopet.domain.model.pet;

import br.com.mascarenhasb2.adopet.domain.model.pet.dto.PetCreationDTO;
import br.com.mascarenhasb2.adopet.domain.model.pet.dto.PetDetailsDTO;
import br.com.mascarenhasb2.adopet.domain.model.pet.dto.PetUpdateDTO;
import br.com.mascarenhasb2.adopet.domain.repository.PetRepository;
import br.com.mascarenhasb2.adopet.domain.repository.ShelterRepository;
import br.com.mascarenhasb2.adopet.infra.exception.dto.ListResponseDTO;
import br.com.mascarenhasb2.adopet.infra.exception.dto.SingleResponseDTO;
import br.com.mascarenhasb2.adopet.util.URIUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class PetService {
    @Autowired
    private ShelterRepository shelterRepository;
    @Autowired
    private PetRepository petRepository;

    public ResponseEntity<SingleResponseDTO> createPet(PetCreationDTO petCreationDTO, UriComponentsBuilder uriBuilder) {
        try{
            var shelter = shelterRepository.getReferenceById(petCreationDTO.shelterId());
            Pet pet = new Pet(petCreationDTO, shelter);
            petRepository.save(pet);
            var uri = URIUtil.createCreatedEntityURI("pets",pet.getId(),uriBuilder);

            return ResponseEntity.created(uri).body(createSingleResponseDTO(HttpStatus.CREATED, pet));
        } catch (EntityNotFoundException exception) {
            throw new EntityNotFoundException();
        }
    }

    public ResponseEntity<ListResponseDTO> readAllPets() {
        var pets = petRepository.findAll();
        if (pets.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var listOfPets = new ListResponseDTO(
                String.valueOf(HttpStatus.OK.value()),
                "Consulta realizada com sucesso!",
                pets.stream().map(PetDetailsDTO::new).toList()
        );

        return ResponseEntity.ok(listOfPets);
    }

    public ResponseEntity<SingleResponseDTO> reaPetById(Long id) {
        try {
            var pet = petRepository.getReferenceById(id);
            return ResponseEntity.ok(createSingleResponseDTO(HttpStatus.OK, pet));
        } catch (EntityNotFoundException exception) {
            throw new EntityNotFoundException();
        }
    }

    public ResponseEntity<SingleResponseDTO> updatePet(PetUpdateDTO petUpdateDTO) {
        try {
            var pet = petRepository.getReferenceById(petUpdateDTO.id());
            var shelter = shelterRepository.getReferenceById(pet.getId());
            pet.updateInformation(petUpdateDTO, shelter);
            return ResponseEntity.ok(createSingleResponseDTO(HttpStatus.OK, pet));
        } catch (EntityNotFoundException exception) {
            throw new EntityNotFoundException();
        }
    }

    public ResponseEntity<SingleResponseDTO> deleteById(Long id) {
        if (petRepository.existsById(id)) {
            petRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        throw new EntityNotFoundException();
    }

    private SingleResponseDTO createSingleResponseDTO(HttpStatus status, Pet pet) {
        return new SingleResponseDTO(
                String.valueOf(status.value()),
                "Operação realizada com sucesso!",
                new PetDetailsDTO(pet)
        );
    }
}
