package pl.ske.project1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.ske.project1.entity.Cart;
import pl.ske.project1.repository.CartRepository;

import java.util.List;
import java.util.Optional;

@Service
@Component
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    public Cart createCartEntry(Cart newCartEntry) {
        return cartRepository.save(newCartEntry);
    }

    public List<Cart> findAll() {
        return (List<Cart>) cartRepository.findAll();
    }

    public Optional<Cart> findById(Long cartId) {
        return cartRepository.findById(cartId);
    }


}

