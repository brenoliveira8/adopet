package br.com.mascarenhasb2.adopet.domain.repository;

import br.com.mascarenhasb2.adopet.domain.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    User findByEmail(String email);
}
