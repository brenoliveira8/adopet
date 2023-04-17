package br.com.mascarenhasb2.adopet.controller;

import br.com.mascarenhasb2.adopet.domain.model.pet.Pet;
import br.com.mascarenhasb2.adopet.domain.model.pet.dto.PetCreationDTO;
import br.com.mascarenhasb2.adopet.domain.model.pet.dto.PetDetailsDTO;
import br.com.mascarenhasb2.adopet.domain.repository.PetRepository;
import br.com.mascarenhasb2.adopet.domain.repository.ShelterRepository;
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
@RequestMapping("pets")
public class PetController {

    @Autowired
    private PetRepository petRepository;
    @Autowired
    private ShelterRepository shelterRepository;

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid PetCreationDTO petCreationDTO, UriComponentsBuilder uriBuilder){
        var shelter = shelterRepository.getReferenceById(petCreationDTO.shelterId());
        Pet pet = new Pet(petCreationDTO, shelter);
        petRepository.save(pet);

        var uri = uriBuilder.path("pets/{id}").buildAndExpand(pet.getId()).toUri();

        return ResponseEntity.created(uri).body(new PetDetailsDTO(pet));
    }

//    @GetMapping
//    public ResponseEntity read(){
//        var shelters = shelterRepository.findAll();
//        if (shelters.isEmpty()){
//            return new ResponseEntity<>("Não encontrado.", HttpStatus.NOT_FOUND);
//        }
//        return ResponseEntity.ok(shelters.stream().map(ShelterDetailsDTO::new).collect(Collectors.toList()));
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity readById(@PathVariable Long id){
//        try{
//            var shelter = shelterRepository.getReferenceById(id);
//            return ResponseEntity.ok(new ShelterDetailsDTO(shelter));
//        }catch (EntityNotFoundException exception){
//            return new ResponseEntity<>("Não encontrado.", HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH})
//    @Transactional
//    public ResponseEntity update(@RequestBody @Valid ShelterUpdateDTO shelterUpdateDTO){
//        try{
//            var shelter = shelterRepository.getReferenceById(shelterUpdateDTO.id());
//            shelter.updateInformation(shelterUpdateDTO);
//            return ResponseEntity.ok(new ShelterDetailsDTO(shelter));
//        }catch (EntityNotFoundException exception){
//            return new ResponseEntity<>("Não encontrado.", HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    @Transactional
//    public ResponseEntity delete(@PathVariable Long id){
//        if(shelterRepository.existsById(id)) {
//            shelterRepository.deleteById(id);
//            return ResponseEntity.noContent().build();
//        }
//        return new ResponseEntity<>("Não encontrado.", HttpStatus.NOT_FOUND);
//    }

}
