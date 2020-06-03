package pl.ske.project1.repository;

import org.springframework.data.repository.CrudRepository;
import pl.ske.project1.entity.Cart;

public interface CartRepository extends CrudRepository<Cart, Long> {
}
