package pl.ske.project1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.ske.project1.entity.ApplicationUser;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    ApplicationUser findByUsername(String username);
}

