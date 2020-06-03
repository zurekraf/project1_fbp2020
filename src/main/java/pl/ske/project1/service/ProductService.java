package pl.ske.project1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.ske.project1.entity.Product;
import pl.ske.project1.repository.ProductRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Component
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    //__________kiedy sami piszemy te operacje____
    @PersistenceContext
    EntityManager em;
    @Autowired
    EntityManagerFactory emf;
    //____________________________________________

    //ręcznie napisany findbyid
    public Optional<Product> findById(Long productId) {
        Product product = em.find(Product.class, productId);
        em.detach(product);
        return Optional.of(product);
    }

    //ręcznie createProduct
    public Product createProduct(Product newProduct) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(newProduct); //sam persist nie zapisuje do bazy, konieczny jest commit
        em.getTransaction().commit();
        em.close();
        return newProduct;
    }

    public List<Product> findall() {
        return (List<Product>) productRepository.findAll();
    }

    /*
    public Optional<Product> findById(Long productId) {
        return productRepository.findById(productId);
    }

    public Product createProduct(Product newProduct) {
        return productRepository.save(newProduct);
    }
     */

    public Optional<Product> replaceProduct(Product newProduct, Long productId) {
        return productRepository.findById(productId).map(product -> {
            product.setName(newProduct.getName()); //pierwszy produkt to ten wyciągnięty z repo, 2gi to ten nowy newProduct
            return productRepository.save(product); //podmieniliśmy name i zapisujemy go
        });
    }

    public Optional<Product> updateProduct(Map<String, Object> updates, Long productId) {
        Optional<Product> productById = productRepository.findById(productId);
        if(productById.isPresent()) {
            Product product = productById.get();
            if(updates.containsKey("name")) {
                product.setName((String) updates.get("name")); //wyciągamy getem wartośc dla klucza name
            }
            if(updates.containsKey("price")) {
                product.setPrice((Integer) updates.get("price"));
            }
            productRepository.save(product);
        }
        return productById;
    }

    public void deleteById(Long productId) {
        productRepository.deleteById(productId);
    }

    public List<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

//    public List<Product> filterByName(String name) {
//        return (List<Product>) productRepository.findByNameContainingIgnoreCase(name);
//    }

    public Slice<Product> filterByName(String name, Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCase(name, pageable);
    }

}
