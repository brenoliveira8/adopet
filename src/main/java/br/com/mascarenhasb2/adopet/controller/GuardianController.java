package br.com.mascarenhasb2.adopet.controller;

import br.com.mascarenhasb2.adopet.domain.service.GuardianService;
import br.com.mascarenhasb2.adopet.domain.model.guardian.dto.GuardianCreationDTO;
import br.com.mascarenhasb2.adopet.domain.model.guardian.dto.GuardianUpdateDTO;
import br.com.mascarenhasb2.adopet.infra.exception.dto.ListResponseDTO;
import br.com.mascarenhasb2.adopet.infra.exception.dto.SingleResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("tutores")
public class GuardianController{
    @Autowired
    private GuardianService guardianService;

    @PostMapping
    @Transactional
    public ResponseEntity<SingleResponseDTO> create(@RequestBody @Valid GuardianCreationDTO guardianCreationDTO, UriComponentsBuilder uriBuilder){
        return guardianService.createGuardian(guardianCreationDTO, uriBuilder);
    }

    @GetMapping
    public ResponseEntity<ListResponseDTO> read(){
        return guardianService.readAllGuardians();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SingleResponseDTO> readById(@PathVariable Long id){
        return guardianService.readGuardianById(id);
    }

    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH})
    @Transactional
    public ResponseEntity<SingleResponseDTO> update(@RequestBody @Valid GuardianUpdateDTO guardianUpdateDTO){
        return guardianService.updateGuardian(guardianUpdateDTO);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<SingleResponseDTO> delete(@PathVariable Long id){
        return guardianService.deleteById(id);
    }
}
