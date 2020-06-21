package pl.ske.project1.repository;

import org.springframework.data.repository.CrudRepository;
import pl.ske.project1.entity.Hearing;

public interface HearingRepository extends CrudRepository<Hearing, Long> {
}
