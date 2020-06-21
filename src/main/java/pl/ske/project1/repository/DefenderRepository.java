package pl.ske.project1.repository;

import org.springframework.data.repository.CrudRepository;
import pl.ske.project1.entity.Defender;

import java.util.List;

public interface DefenderRepository extends CrudRepository<Defender, Long> {
    List<Defender> findByLastName(String lastName);
}
