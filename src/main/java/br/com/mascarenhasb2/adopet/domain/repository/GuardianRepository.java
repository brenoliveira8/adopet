package br.com.mascarenhasb2.adopet.domain.repository;

import br.com.mascarenhasb2.adopet.domain.model.guardian.Guardian;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuardianRepository extends JpaRepository<Guardian, Long>{
}
