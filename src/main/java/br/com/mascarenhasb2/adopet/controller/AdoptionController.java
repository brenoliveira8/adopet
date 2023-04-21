package br.com.mascarenhasb2.adopet.controller;

import br.com.mascarenhasb2.adopet.domain.model.adoption.Adoption;
import br.com.mascarenhasb2.adopet.domain.model.adoption.dto.AdoptionCreationDTO;
import br.com.mascarenhasb2.adopet.domain.model.adoption.dto.AdoptionDetailsDTO;
import br.com.mascarenhasb2.adopet.domain.repository.AdoptionRepository;
import br.com.mascarenhasb2.adopet.domain.repository.GuardianRepository;
import br.com.mascarenhasb2.adopet.domain.repository.PetRepository;
import br.com.mascarenhasb2.adopet.infra.exception.ErrorDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("adocao")
public class AdoptionController {

    @Autowired
    private GuardianRepository guardianRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private AdoptionRepository adoptionRepository;
    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid AdoptionCreationDTO adoptionCreationDTO, UriComponentsBuilder uriComponentsBuilder){
        try {
            var pet = petRepository.getReferenceById(adoptionCreationDTO.petId());
            if(Boolean.TRUE.equals(pet.getAdopted())){
                return new ResponseEntity<>(
                        new ErrorDTO(String.valueOf(HttpStatus.BAD_REQUEST.value()),"Pet já foi adotado."),HttpStatus.BAD_REQUEST
                );
            }
            var guardian = guardianRepository.getReferenceById(adoptionCreationDTO.guardianId());
            Adoption adoption = new Adoption(guardian, pet);
            pet.adopt();
            adoptionRepository.save(adoption);
            return ResponseEntity.ok(new AdoptionDetailsDTO(adoption));
        }catch (EntityNotFoundException exception){
            return new ResponseEntity<>("Tutor ou Pet não encontrado.", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id){
        if (adoptionRepository.existsById(id)){
            adoptionRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>("Não encontrado.", HttpStatus.NOT_FOUND);
    }
}
