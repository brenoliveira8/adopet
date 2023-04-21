package br.com.mascarenhasb2.adopet.controller;

import br.com.mascarenhasb2.adopet.domain.model.guardian.Guardian;
import br.com.mascarenhasb2.adopet.domain.model.guardian.GuardianService;
import br.com.mascarenhasb2.adopet.domain.model.guardian.dto.GuardianCreatedDTO;
import br.com.mascarenhasb2.adopet.domain.model.guardian.dto.GuardianCreationDTO;
import br.com.mascarenhasb2.adopet.domain.model.guardian.dto.GuardianDetailsDTO;
import br.com.mascarenhasb2.adopet.domain.model.guardian.dto.GuardianUpdateDTO;
import br.com.mascarenhasb2.adopet.domain.model.user.Role;
import br.com.mascarenhasb2.adopet.domain.model.user.User;
import br.com.mascarenhasb2.adopet.domain.repository.GuardianRepository;
import br.com.mascarenhasb2.adopet.domain.repository.UserRepository;
import br.com.mascarenhasb2.adopet.infra.exception.EmailConflictException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("tutores")
public class GuardianController {

    @Autowired
    private GuardianRepository guardianRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GuardianService service;

    @PostMapping
    @Transactional
    public ResponseEntity<GuardianCreatedDTO> create(@RequestBody @Valid GuardianCreationDTO guardianCreationDTO, UriComponentsBuilder uriBuilder){
        return service.create(guardianCreationDTO, uriBuilder);
    }

    @GetMapping
    public ResponseEntity<List<GuardianDetailsDTO>> read(){
        return service.read();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GuardianDetailsDTO> readById(@PathVariable Long id){
        return service.readById(id);
    }

    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH})
    @Transactional
    public ResponseEntity update(@RequestBody @Valid GuardianUpdateDTO guardianUpdateDTO){
        try{
            var guardian = guardianRepository.getReferenceById(guardianUpdateDTO.id());
            guardian.updateInformation(guardianUpdateDTO);
            return ResponseEntity.ok(new GuardianDetailsDTO(guardian));
        }catch (EntityNotFoundException exception){
            return new ResponseEntity<>("Não encontrado.", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id){
        if(guardianRepository.existsById(id)) {
            guardianRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>("Não encontrado.", HttpStatus.NOT_FOUND);
    }

}
