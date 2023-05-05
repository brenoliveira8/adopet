package br.com.mascarenhasb2.adopet.controller;

import br.com.mascarenhasb2.adopet.domain.model.adoption.AdoptionService;
import br.com.mascarenhasb2.adopet.domain.model.adoption.dto.AdoptionCreationDTO;
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
@RequestMapping("adocao")
public class AdoptionController{

    @Autowired
    private AdoptionService adoptionService;

    @PostMapping
    @Transactional
    public ResponseEntity<SingleResponseDTO> create(@RequestBody @Valid AdoptionCreationDTO adoptionCreationDTO, UriComponentsBuilder uriBuilder){
        return adoptionService.createAdoption(adoptionCreationDTO, uriBuilder);
    }

    @GetMapping
    public ResponseEntity<Page<ListResponseDTO>> read(@PageableDefault(size = 10, page = 0, sort = {"id"}) Pageable pageable){
        return adoptionService.readAllAdoptionsPaginated(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SingleResponseDTO> readById(@PathVariable Long id){
        return adoptionService.readAdoptionById(id);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<SingleResponseDTO> delete(@PathVariable Long id){
        return adoptionService.deleteById(id);
    }
}
