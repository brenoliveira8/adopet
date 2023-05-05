package br.com.mascarenhasb2.adopet.domain.service;

import br.com.mascarenhasb2.adopet.domain.model.adoption.Adoption;
import br.com.mascarenhasb2.adopet.domain.model.adoption.dto.AdoptionCreationDTO;
import br.com.mascarenhasb2.adopet.domain.model.adoption.dto.AdoptionDetailsDTO;
import br.com.mascarenhasb2.adopet.domain.repository.AdoptionRepository;
import br.com.mascarenhasb2.adopet.domain.repository.GuardianRepository;
import br.com.mascarenhasb2.adopet.domain.repository.PetRepository;
import br.com.mascarenhasb2.adopet.infra.exception.PetAlreadyAdoptedException;
import br.com.mascarenhasb2.adopet.infra.exception.dto.ListResponseDTO;
import br.com.mascarenhasb2.adopet.infra.exception.dto.SingleResponseDTO;
import br.com.mascarenhasb2.adopet.util.URIUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;

@Service
public class AdoptionService{
    @Autowired
    private GuardianRepository guardianRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private AdoptionRepository adoptionRepository;

    public ResponseEntity<SingleResponseDTO> createAdoption(AdoptionCreationDTO adoptionCreationDTO, UriComponentsBuilder uriBuilder){
        try{
            var guardian = guardianRepository.getReferenceById(adoptionCreationDTO.guardianId());
            var pet = petRepository.getReferenceById(adoptionCreationDTO.petId());
            if(Boolean.TRUE.equals(pet.getAdopted())){
                throw new PetAlreadyAdoptedException("Pet já foi adotado.");
            }
            Adoption adoption = new Adoption(guardian, pet);
            pet.adopt(Boolean.TRUE);
            adoptionRepository.save(adoption);
            var uri = URIUtil.createCreatedEntityURI("adocao", adoption.getId(), uriBuilder);

            return ResponseEntity.created(uri).body(createSingleResponseDTO(HttpStatus.CREATED, adoption));
        }catch(EntityNotFoundException exception){
            throw new EntityNotFoundException();
        }
    }

    public ResponseEntity<Page<ListResponseDTO>> readAllAdoptionsPaginated(Pageable pageable){
        var adoptions = adoptionRepository.findAll(pageable);
        if(adoptions.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        var listOfAdoptions = new ListResponseDTO(
                String.valueOf(HttpStatus.OK.value()),
                "Consulta realizada com sucesso!",
                adoptions.stream().map(AdoptionDetailsDTO::new).toList()
        );

        return ResponseEntity.ok(new PageImpl<>(Collections.singletonList(listOfAdoptions), pageable, adoptions.getTotalElements()));
    }

    public ResponseEntity<SingleResponseDTO> readAdoptionById(Long id){
        try{
            var adoption = adoptionRepository.getReferenceById(id);
            return ResponseEntity.ok(createSingleResponseDTO(HttpStatus.OK, adoption));
        }catch(EntityNotFoundException exception){
            throw new EntityNotFoundException();
        }
    }

    public ResponseEntity<SingleResponseDTO> deleteById(Long id){
        try{
            var adoption = adoptionRepository.getReferenceById(id);
            var pet = petRepository.getReferenceById(adoption.getPet().getId());
            pet.adopt(Boolean.FALSE);

            adoptionRepository.delete(adoption);
            return ResponseEntity.noContent().build();
        }catch(EntityNotFoundException exception){
            throw new EntityNotFoundException();
        }
    }

    private SingleResponseDTO createSingleResponseDTO(HttpStatus status, Adoption adoption){
        return new SingleResponseDTO(
                String.valueOf(status.value()),
                "Operação realizada com sucesso!",
                new AdoptionDetailsDTO(adoption)
        );
    }
}
