package pl.ske.project1.HATEOAS;


import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import pl.ske.project1.entity.Product;
import pl.ske.project1.restservice.ProductController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductModelAssembler implements RepresentationModelAssembler<Product, EntityModel<Product>> {
    @Override
    public EntityModel<Product> toModel(Product product) {

        EntityModel<Product> productEntityModel = EntityModel.of(product);
        Link selfLink = linkTo(methodOn(ProductController.class).getProductById(product.getId())).withSelfRel();
        Link allLink = linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products");
        productEntityModel.add(selfLink, allLink);

//        return EntityModel.of(product.get(),
//                linkTo(methodOn(ProductController.class).getProductById(id)).withSelfRel(),
//                linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products"));

        return productEntityModel;
    }
}
