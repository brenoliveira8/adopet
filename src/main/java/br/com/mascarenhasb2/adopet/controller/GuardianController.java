package br.com.mascarenhasb2.adopet.controller;

import br.com.mascarenhasb2.adopet.domain.model.guardian.Guardian;
import br.com.mascarenhasb2.adopet.domain.model.guardian.dto.GuardianCreatedDTO;
import br.com.mascarenhasb2.adopet.domain.model.guardian.dto.GuardianCreationDTO;
import br.com.mascarenhasb2.adopet.domain.model.guardian.dto.GuardianDetailsDTO;
import br.com.mascarenhasb2.adopet.domain.model.guardian.dto.GuardianUpdateDTO;
import br.com.mascarenhasb2.adopet.domain.model.user.Role;
import br.com.mascarenhasb2.adopet.domain.model.user.User;
import br.com.mascarenhasb2.adopet.domain.repository.GuardianRepository;
import br.com.mascarenhasb2.adopet.domain.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.stream.Collectors;

@RestController
@RequestMapping("tutores")
public class GuardianController {

    @Autowired
    private GuardianRepository guardianRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid GuardianCreationDTO guardianCreationDTO, UriComponentsBuilder uriBuilder){
        String encodedPassword = encodePassword(guardianCreationDTO.password());
        User user = new User(guardianCreationDTO.email(), encodedPassword, Role.GUARDIAN);
        userRepository.save(user);
        Guardian guardian = new Guardian(guardianCreationDTO, user);
        guardianRepository.save(guardian);

        var uri = uriBuilder.path("tutores/{id}").buildAndExpand(guardian.getId()).toUri();

        return ResponseEntity.created(uri).body(new GuardianCreatedDTO(guardian));
    }
    private String encodePassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    @GetMapping
    public ResponseEntity read(){
        var guardians = guardianRepository.findAll();
        if (guardians.isEmpty()){
            return new ResponseEntity<>("Não encontrado.", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(guardians.stream().map(GuardianDetailsDTO::new).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity readById(@PathVariable Long id){
        try{
            var guardian = guardianRepository.getReferenceById(id);
            return ResponseEntity.ok(new GuardianDetailsDTO(guardian));
        }catch (EntityNotFoundException exception){
            return new ResponseEntity<>("Não encontrado.", HttpStatus.NOT_FOUND);
        }
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
