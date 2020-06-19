package pl.ske.project1.repository;

import org.springframework.data.repository.CrudRepository;
import pl.ske.project1.entity.CourtCase;

public interface CourtCaseRepository extends CrudRepository<CourtCase, Long> {
}
