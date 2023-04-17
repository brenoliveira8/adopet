package br.com.mascarenhasb2.adopet.domain.repository;

import br.com.mascarenhasb2.adopet.domain.model.shelter.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelterRepository extends JpaRepository<Shelter, Long> {
}
