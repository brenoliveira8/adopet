package br.com.mascarenhasb2.adopet.domain.repository;

import br.com.mascarenhasb2.adopet.domain.model.pet.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
}
