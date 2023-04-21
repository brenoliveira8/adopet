package br.com.mascarenhasb2.adopet.domain.model.guardian;

import br.com.mascarenhasb2.adopet.domain.model.guardian.dto.GuardianCreatedDTO;
import br.com.mascarenhasb2.adopet.domain.model.guardian.dto.GuardianCreationDTO;
import br.com.mascarenhasb2.adopet.domain.model.guardian.dto.GuardianDetailsDTO;
import br.com.mascarenhasb2.adopet.domain.model.user.Role;
import br.com.mascarenhasb2.adopet.domain.model.user.User;
import br.com.mascarenhasb2.adopet.domain.repository.GuardianRepository;
import br.com.mascarenhasb2.adopet.domain.repository.UserRepository;
import br.com.mascarenhasb2.adopet.util.EmailUtil;
import br.com.mascarenhasb2.adopet.util.PasswordUtil;
import br.com.mascarenhasb2.adopet.util.URIUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GuardianService {
    @Autowired
    private GuardianRepository guardianRepository;
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<GuardianCreatedDTO> create(GuardianCreationDTO guardianCreationDTO, UriComponentsBuilder uriBuilder){
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

        return ResponseEntity.created(uri).body(new GuardianCreatedDTO(guardian));
    }
    public ResponseEntity<List<GuardianDetailsDTO>> read() {
        var guardians = guardianRepository.findAll();
        if (guardians.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(guardians.stream().map(GuardianDetailsDTO::new).collect(Collectors.toList()));
    }

    public ResponseEntity<GuardianDetailsDTO> readById(Long id){
        try{
            var guardian = guardianRepository.getReferenceById(id);
            return ResponseEntity.ok(new GuardianDetailsDTO(guardian));
        }catch (EntityNotFoundException exception){
            throw new EntityNotFoundException();
        }
    }
}
