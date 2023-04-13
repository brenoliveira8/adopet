package br.com.mascarenhasb2.adopet.controller;

import br.com.mascarenhasb2.adopet.domain.guardian.Guardian;
import br.com.mascarenhasb2.adopet.domain.guardian.dto.GuardianCreatedDTO;
import br.com.mascarenhasb2.adopet.domain.guardian.dto.GuardianCreationDTO;
import br.com.mascarenhasb2.adopet.domain.guardian.dto.GuardianDetailsDTO;
import br.com.mascarenhasb2.adopet.domain.guardian.repository.GuardianRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("tutores")
public class GuardianController {

    @Autowired
    private GuardianRepository guardianRepository;

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid GuardianCreationDTO data, UriComponentsBuilder uriBuilder){
        Guardian guardian = new Guardian(data);
        guardianRepository.save(guardian);

        var uri = uriBuilder.path("tutores/{id}").buildAndExpand(guardian.getId()).toUri();

        return ResponseEntity.created(uri).body(new GuardianCreatedDTO(guardian));
    }

}
