package br.com.mascarenhasb2.adopet.controller;

import br.com.mascarenhasb2.adopet.domain.model.guardian.dto.GuardianDetailsDTO;
import br.com.mascarenhasb2.adopet.domain.model.shelter.Shelter;
import br.com.mascarenhasb2.adopet.domain.model.shelter.dto.ShelterDetailsDTO;
import br.com.mascarenhasb2.adopet.domain.model.shelter.dto.ShelterCreationDTO;
import br.com.mascarenhasb2.adopet.domain.model.shelter.dto.ShelterUpdateDTO;
import br.com.mascarenhasb2.adopet.domain.repository.ShelterRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.stream.Collectors;

@RestController
@RequestMapping("abrigos")
public class ShelterController {

    @Autowired
    private ShelterRepository shelterRepository;

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid ShelterCreationDTO shelterCreationDTO, UriComponentsBuilder uriBuilder){
        Shelter shelter = new Shelter(shelterCreationDTO);
        shelterRepository.save(shelter);

        var uri = uriBuilder.path("abrigos/{id}").buildAndExpand(shelter.getId()).toUri();

        return ResponseEntity.created(uri).body(new ShelterDetailsDTO(shelter));
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

//    @DeleteMapping("/{id}")
//    @Transactional
//    public ResponseEntity delete(@PathVariable Long id){
//        if(guardianRepository.existsById(id)) {
//            guardianRepository.deleteById(id);
//            return ResponseEntity.noContent().build();
//        }
//        return new ResponseEntity<>("Não encontrado.", HttpStatus.NOT_FOUND);
//    }
//
}
