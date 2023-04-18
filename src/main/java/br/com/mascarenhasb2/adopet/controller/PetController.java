package br.com.mascarenhasb2.adopet.controller;

import br.com.mascarenhasb2.adopet.domain.model.pet.Pet;
import br.com.mascarenhasb2.adopet.domain.model.pet.dto.PetCreationDTO;
import br.com.mascarenhasb2.adopet.domain.model.pet.dto.PetDetailsDTO;
import br.com.mascarenhasb2.adopet.domain.model.pet.dto.PetUpdateDTO;
import br.com.mascarenhasb2.adopet.domain.repository.PetRepository;
import br.com.mascarenhasb2.adopet.domain.repository.ShelterRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.stream.Collectors;

@RestController
@RequestMapping ("pets")
public class PetController {

    @Autowired
    private PetRepository petRepository;
    @Autowired
    private ShelterRepository shelterRepository;

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid PetCreationDTO petCreationDTO, UriComponentsBuilder uriBuilder) {
        var shelter = shelterRepository.getReferenceById(petCreationDTO.shelterId());
        Pet pet = new Pet(petCreationDTO, shelter);
        petRepository.save(pet);

        var uri = uriBuilder.path("pets/{id}").buildAndExpand(pet.getId()).toUri();

        return ResponseEntity.created(uri).body(new PetDetailsDTO(pet));
    }

    @GetMapping
    public ResponseEntity read() {
        var pets = petRepository.findAllByAdoptedFalse();
        if (pets.isEmpty()) {
            return new ResponseEntity<>("Não encontrado.", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(pets.stream().map(PetDetailsDTO::new).collect(Collectors.toList()));
    }

    @GetMapping ("/{id}")
    public ResponseEntity readById(@PathVariable Long id) {
        try {
            var pet = petRepository.getReferenceById(id);
            return ResponseEntity.ok(new PetDetailsDTO(pet));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>("Não encontrado.", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping (method = {RequestMethod.PUT, RequestMethod.PATCH})
    @Transactional
    public ResponseEntity update(@RequestBody @Valid PetUpdateDTO petUpdateDTO) {
        try {
            var pet = petRepository.getReferenceById(petUpdateDTO.id());
            var shelter = shelterRepository.getReferenceById(pet.getId());
            pet.updateInformation(petUpdateDTO, shelter);
            return ResponseEntity.ok(new PetDetailsDTO(pet));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>("Não encontrado.", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping ("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id) {
        if (petRepository.existsById(id)) {
            petRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>("Não encontrado.", HttpStatus.NOT_FOUND);
    }

}
