package pl.ske.project1.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ske.project1.entity.Cart;
import pl.ske.project1.entity.Product;
import pl.ske.project1.service.CartService;
import pl.ske.project1.service.ProductService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;

    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<List<Cart>> getAllCartItems() {
        return ResponseEntity.ok(cartService.findAll());
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Cart> getCartById(@PathVariable Long id) {
        Optional<Cart> cart = cartService.findById(id);
        return ResponseEntity.ok(cart.get());
    }

    @PostMapping("/add/{productId}")
    public ResponseEntity<?> addCartItem(@PathVariable (value = "productId") Long productId, @RequestBody Cart cart) {
        Optional<Product> product = productService.findById(productId);
        cart.setProduct(product.get());
        cartService.createCartEntry(cart);
        URI uri = URI.create(String.format("/api/carts/%s", cart.getId()));
        return ResponseEntity.created(uri).body("Added to cart"); //czyli jeśli wszystko ok to powinniśmy dodać http 201
    }

}
