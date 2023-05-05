package br.com.mascarenhasb2.adopet.domain.model.shelter;

import br.com.mascarenhasb2.adopet.domain.model.shelter.dto.ShelterCreationDTO;
import br.com.mascarenhasb2.adopet.domain.model.shelter.dto.ShelterDetailsDTO;
import br.com.mascarenhasb2.adopet.domain.model.shelter.dto.ShelterUpdateDTO;
import br.com.mascarenhasb2.adopet.domain.model.user.Role;
import br.com.mascarenhasb2.adopet.domain.model.user.User;
import br.com.mascarenhasb2.adopet.domain.repository.ShelterRepository;
import br.com.mascarenhasb2.adopet.domain.repository.UserRepository;
import br.com.mascarenhasb2.adopet.infra.exception.dto.ListResponseDTO;
import br.com.mascarenhasb2.adopet.infra.exception.dto.SingleResponseDTO;
import br.com.mascarenhasb2.adopet.util.EmailUtil;
import br.com.mascarenhasb2.adopet.util.URIUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ShelterService{
    @Autowired
    private ShelterRepository shelterRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<SingleResponseDTO> createShelter(ShelterCreationDTO shelterCreationDTO, UriComponentsBuilder uriBuilder){
        EmailUtil.verifyIfExists(userRepository, shelterCreationDTO.user().email());
        String encodedPassword = passwordEncoder.encode(shelterCreationDTO.user().password());

        User user = new User(shelterCreationDTO.user().email(), encodedPassword, Role.SHELTER);
        userRepository.save(user);
        Shelter shelter = new Shelter(shelterCreationDTO, user);
        shelterRepository.save(shelter);

        var uri = URIUtil.createCreatedEntityURI("abrigos", shelter.getId(), uriBuilder);

        return ResponseEntity.created(uri).body(createSingleResponseDTO(HttpStatus.CREATED, shelter));
    }

    public ResponseEntity<ListResponseDTO> readAllShelters(){
        var shelters = shelterRepository.findAll();
        if(shelters.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        var listOfShelters = new ListResponseDTO(
                String.valueOf(HttpStatus.OK.value()),
                "Consulta realizada com sucesso!",
                shelters.stream().map(ShelterDetailsDTO::new).toList()
        );

        return ResponseEntity.ok(listOfShelters);
    }

    public ResponseEntity<SingleResponseDTO> readShelterById(Long id){
        try{
            var shelter = shelterRepository.getReferenceById(id);
            return ResponseEntity.ok(createSingleResponseDTO(HttpStatus.OK, shelter));
        }catch(EntityNotFoundException exception){
            throw new EntityNotFoundException();
        }
    }

    public ResponseEntity<SingleResponseDTO> updateShelter(ShelterUpdateDTO shelterUpdateDTO){
        try{
            var shelter = shelterRepository.getReferenceById(shelterUpdateDTO.id());
            shelter.updateInformation(shelterUpdateDTO);
            return ResponseEntity.ok(createSingleResponseDTO(HttpStatus.OK, shelter));
        }catch(EntityNotFoundException exception){
            throw new EntityNotFoundException();
        }
    }

    public ResponseEntity<SingleResponseDTO> deleteById(Long id){
        if(shelterRepository.existsById(id)){
            shelterRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        throw new EntityNotFoundException();
    }

    private SingleResponseDTO createSingleResponseDTO(HttpStatus status, Shelter shelter){
        return new SingleResponseDTO(
                String.valueOf(status.value()),
                "Operação realizada com sucesso!",
                new ShelterDetailsDTO(shelter)
        );
    }

}
