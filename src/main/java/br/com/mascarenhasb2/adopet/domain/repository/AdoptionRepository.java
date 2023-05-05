package br.com.mascarenhasb2.adopet.domain.repository;

import br.com.mascarenhasb2.adopet.domain.model.adoption.Adoption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdoptionRepository extends JpaRepository<Adoption, Long>{
}
