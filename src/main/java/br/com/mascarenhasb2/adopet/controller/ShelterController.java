package br.com.mascarenhasb2.adopet.controller;

import br.com.mascarenhasb2.adopet.domain.model.guardian.dto.GuardianDetailsDTO;
import br.com.mascarenhasb2.adopet.domain.model.shelter.Shelter;
import br.com.mascarenhasb2.adopet.domain.model.shelter.dto.ShelterDetailsDTO;
import br.com.mascarenhasb2.adopet.domain.model.shelter.dto.ShelterCreationDTO;
import br.com.mascarenhasb2.adopet.domain.model.shelter.dto.ShelterUpdateDTO;
import br.com.mascarenhasb2.adopet.domain.model.user.Role;
import br.com.mascarenhasb2.adopet.domain.model.user.User;
import br.com.mascarenhasb2.adopet.domain.repository.ShelterRepository;
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
@RequestMapping("abrigos")
public class ShelterController {

    @Autowired
    private ShelterRepository shelterRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid ShelterCreationDTO shelterCreationDTO, UriComponentsBuilder uriBuilder){
        if (userRepository.existsByEmail(shelterCreationDTO.user().email())){
            return new ResponseEntity<>("E-mail já existe.", HttpStatus.CONFLICT);
        }

        String encodedPassword = encodePassword(shelterCreationDTO.user().password());
        User user = new User(shelterCreationDTO.user().email(),encodedPassword, Role.SHELTER);
        userRepository.save(user);
        Shelter shelter = new Shelter(shelterCreationDTO,user);
        shelterRepository.save(shelter);

        var uri = uriBuilder.path("abrigos/{id}").buildAndExpand(shelter.getId()).toUri();

        return ResponseEntity.created(uri).body(new ShelterDetailsDTO(shelter));
    }

    private String encodePassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    @GetMapping
    public ResponseEntity read(){
        var shelters = shelterRepository.findAll();
        if (shelters.isEmpty()){
            return new ResponseEntity<>("Não encontrado.", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(shelters.stream().map(ShelterDetailsDTO::new).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity readById(@PathVariable Long id){
        try{
            var shelter = shelterRepository.getReferenceById(id);
            return ResponseEntity.ok(new ShelterDetailsDTO(shelter));
        }catch (EntityNotFoundException exception){
            return new ResponseEntity<>("Não encontrado.", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH})
    @Transactional
    public ResponseEntity update(@RequestBody @Valid ShelterUpdateDTO shelterUpdateDTO){
        try{
            var shelter = shelterRepository.getReferenceById(shelterUpdateDTO.id());
            shelter.updateInformation(shelterUpdateDTO);
            return ResponseEntity.ok(new ShelterDetailsDTO(shelter));
        }catch (EntityNotFoundException exception){
            return new ResponseEntity<>("Não encontrado.", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id){
        if(shelterRepository.existsById(id)) {
            shelterRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>("Não encontrado.", HttpStatus.NOT_FOUND);
    }

}
