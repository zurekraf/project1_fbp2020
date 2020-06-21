package pl.ske.project1.repository;

import org.springframework.data.repository.CrudRepository;
import pl.ske.project1.entity.Sentence;

public interface SentenceRepository extends CrudRepository<Sentence, Long> {
}
