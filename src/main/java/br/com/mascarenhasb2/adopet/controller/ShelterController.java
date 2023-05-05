package br.com.mascarenhasb2.adopet.controller;

import br.com.mascarenhasb2.adopet.domain.service.ShelterService;
import br.com.mascarenhasb2.adopet.domain.model.shelter.dto.ShelterCreationDTO;
import br.com.mascarenhasb2.adopet.domain.model.shelter.dto.ShelterUpdateDTO;
import br.com.mascarenhasb2.adopet.infra.exception.dto.ListResponseDTO;
import br.com.mascarenhasb2.adopet.infra.exception.dto.SingleResponseDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("abrigos")
@SecurityRequirement(name = "bearer-key")
public class ShelterController{
    @Autowired
    private ShelterService shelterService;

    @PostMapping
    @Transactional
    public ResponseEntity<SingleResponseDTO> create(@RequestBody @Valid ShelterCreationDTO shelterCreationDTO, UriComponentsBuilder uriBuilder){
        return shelterService.createShelter(shelterCreationDTO, uriBuilder);
    }

    @GetMapping
    public ResponseEntity<ListResponseDTO> read(){
        return shelterService.readAllShelters();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SingleResponseDTO> readById(@PathVariable Long id){
        return shelterService.readShelterById(id);
    }

    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH})
    @Transactional
    public ResponseEntity<SingleResponseDTO> update(@RequestBody @Valid ShelterUpdateDTO shelterUpdateDTO){
        return shelterService.updateShelter(shelterUpdateDTO);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<SingleResponseDTO> delete(@PathVariable Long id){
        return shelterService.deleteById(id);
    }
}
