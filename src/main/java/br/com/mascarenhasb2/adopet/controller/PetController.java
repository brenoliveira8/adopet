package br.com.mascarenhasb2.adopet.controller;

import br.com.mascarenhasb2.adopet.domain.model.pet.PetService;
import br.com.mascarenhasb2.adopet.domain.model.pet.dto.PetCreationDTO;
import br.com.mascarenhasb2.adopet.domain.model.pet.dto.PetUpdateDTO;
import br.com.mascarenhasb2.adopet.infra.exception.dto.ListResponseDTO;
import br.com.mascarenhasb2.adopet.infra.exception.dto.SingleResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("pets")
public class PetController{

    @Autowired
    private PetService petService;

    @PostMapping
    @Transactional
    public ResponseEntity<SingleResponseDTO> create(@RequestBody @Valid PetCreationDTO petCreationDTO, UriComponentsBuilder uriBuilder){
        return petService.createPet(petCreationDTO, uriBuilder);
    }

    @GetMapping
    public ResponseEntity<Page<ListResponseDTO>> read(@PageableDefault(size = 10, page = 0, sort = {"id"}) Pageable pageable){
        return petService.readAllPetsNonAdoptedPetsPaginated(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SingleResponseDTO> readById(@PathVariable Long id){
        return petService.readPetById(id);
    }

    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH})
    @Transactional
    public ResponseEntity<SingleResponseDTO> update(@RequestBody @Valid PetUpdateDTO petUpdateDTO){
        return petService.updatePet(petUpdateDTO);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<SingleResponseDTO> delete(@PathVariable Long id){
        return petService.deleteById(id);
    }

}
