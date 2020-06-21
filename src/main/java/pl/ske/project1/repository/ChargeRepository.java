package pl.ske.project1.repository;

import org.springframework.data.repository.CrudRepository;
import pl.ske.project1.entity.Charge;

public interface ChargeRepository extends CrudRepository<Charge, Long> {
}
