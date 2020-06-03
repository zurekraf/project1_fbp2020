package pl.ske.project1.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.CrudRepository;
import pl.ske.project1.entity.Product;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findByName(String name);
//    List<Product> findByNameContainingIgnoreCase(String name);
    Slice<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
