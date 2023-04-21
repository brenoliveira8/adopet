package br.com.mascarenhasb2.adopet.domain.model.guardian;

import br.com.mascarenhasb2.adopet.domain.model.guardian.dto.GuardianCreatedDTO;
import br.com.mascarenhasb2.adopet.domain.model.guardian.dto.GuardianCreationDTO;
import br.com.mascarenhasb2.adopet.domain.model.guardian.dto.GuardianDetailsDTO;
import br.com.mascarenhasb2.adopet.domain.model.guardian.dto.GuardianUpdateDTO;
import br.com.mascarenhasb2.adopet.domain.model.user.Role;
import br.com.mascarenhasb2.adopet.domain.model.user.User;
import br.com.mascarenhasb2.adopet.domain.repository.GuardianRepository;
import br.com.mascarenhasb2.adopet.domain.repository.UserRepository;
import br.com.mascarenhasb2.adopet.infra.exception.dto.ListResponseDTO;
import br.com.mascarenhasb2.adopet.infra.exception.dto.SingleResponseDTO;
import br.com.mascarenhasb2.adopet.util.EmailUtil;
import br.com.mascarenhasb2.adopet.util.PasswordUtil;
import br.com.mascarenhasb2.adopet.util.URIUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class GuardianService {
    @Autowired
    private GuardianRepository guardianRepository;
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<SingleResponseDTO> createGuardian(GuardianCreationDTO guardianCreationDTO, UriComponentsBuilder uriBuilder){
        EmailUtil.verifyIfExists(userRepository, guardianCreationDTO.user().email());

        String encodedPassword = PasswordUtil.encode(guardianCreationDTO.user().password());

        User user = new User(guardianCreationDTO.user().email(), encodedPassword, Role.GUARDIAN);
        userRepository.save(user);
        Guardian guardian = new Guardian(guardianCreationDTO, user);
        guardianRepository.save(guardian);

        var uri = URIUtil.createCreatedEntityURI(
                "tutores",
                guardian.getId(),
                uriBuilder
        );
        var createdGuardianDTO = new SingleResponseDTO(
                String.valueOf(HttpStatus.CREATED.value()),
                "Tutor criado com sucesso!",
                new GuardianCreatedDTO(guardian)
        );

        return ResponseEntity.created(uri).body(createdGuardianDTO);
    }
    public ResponseEntity<ListResponseDTO> readAllGuardians() {
        var guardians = guardianRepository.findAll();
        if (guardians.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        var listOfGuardians = new ListResponseDTO(
                String.valueOf( HttpStatus.OK.value()),
                "Consulta realizada com sucesso!",
                guardians.stream().map(GuardianDetailsDTO::new).toList()
        );

        return ResponseEntity.ok(listOfGuardians);
    }

    public ResponseEntity<SingleResponseDTO> readGuardianById(Long id){
        try{
            var guardian = guardianRepository.getReferenceById(id);
            return ResponseEntity.ok(createSingleResponseDTO(guardian));
        }catch (EntityNotFoundException exception){
            throw new EntityNotFoundException();
        }
    }

    public ResponseEntity<SingleResponseDTO> updateGuardian(GuardianUpdateDTO guardianUpdateDTO){
        try{
            var guardian = guardianRepository.getReferenceById(guardianUpdateDTO.id());
            guardian.updateInformation(guardianUpdateDTO);
            return ResponseEntity.ok(createSingleResponseDTO(guardian));
        }catch (EntityNotFoundException exception){
            throw new EntityNotFoundException();
        }
    }

    public ResponseEntity<SingleResponseDTO> deleteById(Long id){
        if(guardianRepository.existsById(id)) {
            guardianRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        throw new EntityNotFoundException();
    }

    private SingleResponseDTO createSingleResponseDTO(Guardian guardian){
        return new SingleResponseDTO(
                String.valueOf(HttpStatus.OK.value()),
                "Operação realizada com sucesso!",
                new GuardianDetailsDTO(guardian)
        );
    }

}
