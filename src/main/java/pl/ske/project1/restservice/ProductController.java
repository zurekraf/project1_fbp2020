package pl.ske.project1.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import pl.ske.project1.HATEOAS.ProductModelAssembler;
import pl.ske.project1.entity.Product;
import pl.ske.project1.service.ProductService;
import pl.ske.project1.service.UserService;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductModelAssembler productModelAssembler;

//    @GetMapping(value = "/all", produces = "application/json")
//    public ResponseEntity<List<Product>> getAllProducts() {
//        return ResponseEntity.ok(productService.findall());
//    }

    @GetMapping(value = "/all", produces = "application/hal+json")
    public CollectionModel<EntityModel<Product>> getAllProducts() {

//        List<EntityModel<Product>> products = productService.findall().stream().map(product ->
//                EntityModel.of(product, linkTo(methodOn(ProductController.class).getProductById(product.getId())).withSelfRel(),
//                                        linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products")))
//                .collect(Collectors.toList());
//
//        return CollectionModel.of(products, linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel());
        List<EntityModel<Product>> products = productService.findall().stream()
                .map(productModelAssembler::toModel)
                .collect(Collectors.toList());
        Link selfLink = linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel();

        return CollectionModel.of(products, selfLink);
    }

    @GetMapping(value = "/{id}", produces = "application/hal+json")
    public EntityModel<Product> getProductById(@PathVariable Long id) {

        Optional<Product> product = productService.findById(id);

//        EntityModel<Product> productEntityModel = EntityModel.of(product.get());
//        Link selfLink = linkTo(methodOn(ProductController.class).getProductById(id)).withSelfRel();
//        Link allLink = linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products");
//        productEntityModel.add(selfLink, allLink);

        //return productEntityModel;
        return productModelAssembler.toModel(product.get());

    }

//    @GetMapping(value = "/{id}", produces = "application/json")
//    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
//        Optional<Product> product = productService.findById(id);
//        return ResponseEntity.ok(product.get()); //poleci 500 jeśli podamy id którego nie ma
//    }

//    @PreAuthorize("hasRole('ADMIN')")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostMapping("/add")
    public Product newProduct(@RequestBody Product newProduct) {
        return productService.createProduct(newProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> replaceProduct(@RequestBody Product product, @PathVariable Long id) {
        Optional<Product> replacedProduct = productService.replaceProduct(product, id);
        return ResponseEntity.of(replacedProduct);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody Map<String, Object> updates, @PathVariable Long id) {
        Optional<Product> updatedProduct = productService.updateProduct(updates, id);
        return ResponseEntity.of(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @GetMapping(value = "/byname", produces = "application/json")
    public List<Product> getProductByName(String name) {
        return productService.findByName(name);
    }

//    @GetMapping(value = "/filterbyname", produces = "application/json")
//    public List<Product> filterProductByName(String name) {
//        if(name==null) {
//            return productService.findall();
//        } else {
//            return productService.filterByName(name);
//        }
//    }

    @GetMapping(value = "/filterbyname", produces = "application/json")
    public Slice<Product> filterProductByName(String name, Pageable pageable) {
        if(name==null) {
//            return productService.findall();
            return productService.filterByName("", pageable);
        } else {
            return productService.filterByName(name, pageable);
        }
    }

}
