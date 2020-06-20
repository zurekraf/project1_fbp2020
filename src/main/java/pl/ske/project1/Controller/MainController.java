package pl.ske.project1.Controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.*;
import org.springframework.hateoas.client.Traverson;
import org.springframework.hateoas.server.core.TypeReferences;
import org.springframework.hateoas.server.core.TypeReferences.CollectionModelType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import pl.ske.project1.DTO.DefenderDTO;
import pl.ske.project1.entity.ApplicationUser;
import pl.ske.project1.entity.Charge;
import pl.ske.project1.entity.Product;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import pl.ske.project1.repository.ApplicationUserRepository;
import pl.ske.project1.restservice.ProductController;
import pl.ske.project1.restservice.ProductController.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.swing.text.html.parser.Entity;

import static java.lang.String.valueOf;
import static org.springframework.hateoas.client.Hop.rel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Controller
public class MainController {

//    @Autowired
//    ApplicationUserRepository applicationUserRepository;

//    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
//    @GetMapping("/supertest")
//    //@PostAuthorize("#username == authentication.principal.username")
//    public void idtest(Authentication authentication) {
//
//        System.out.println(authentication.getName());
//
//
//    }

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    //@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/index2")
    public String test22(Model model) {

//        String uri = "http://localhost:8080/api/defenders";
//        Traverson traverson = new Traverson(URI.create(uri), MediaTypes.HAL_JSON);
//        Traverson.TraversalBuilder tb = traverson.follow("href");
//
//        ParameterizedTypeReference<CollectionModel<DefenderDTO>> typeReference = new ParameterizedTypeReference<CollectionModel<DefenderDTO>>() {};
//        CollectionModel<DefenderDTO> resDefenders = tb.toObject(typeReference);
//        Collection<DefenderDTO> defenders = resDefenders.getContent();
//
//        ArrayList<DefenderDTO> defendersList = new ArrayList<>(defenders);
//
//        defendersList.forEach(System.out::println);
//
//        for(DefenderDTO x : defendersList) {
//            System.out.println(x);
//            System.out.println(x.getLinks());
//        }

        return "testPage";
    }

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    @GetMapping("/index")
    public String test(Model model) {

        String uri = "http://localhost:8080/api/products/all";
        RestTemplate restTemplate = new RestTemplate();

        Link link1 = linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel();

//        WebClient.Builder builder = WebClient.builder(); //to do beana można wyrzucić
//        Product[] products = builder.build()
//                .get() //albo .post
//                .uri(link1.toUri())
//                .retrieve()
//                .bodyToMono(Product[].class)
//                .block();
        //mono to takie promise w javie bo to asynchronicznie idzie
        //block czeka aż mono do nas wróci. Czyli takie blokowanie.


        //ResponseEntity<Product[]> response = restTemplate.getForEntity(uri, Product[].class);
//        Product[] response = restTemplate.getForObject(uri, Product[].class);

//        response = restTemplate.getForObject(uri, EntityModel<>);

        //Product[] products = response.getBody();
//
//        List<Product> productsList = Arrays.asList(products);
//
//        model.addAttribute("products", productsList);

        //TypeReferences.CollectionModelType<Product> collectionModelType = TypeReferences.CollectionModelType<Product>() {};

        Traverson traverson = new Traverson(URI.create(uri), MediaTypes.HAL_JSON);
        Traverson.TraversalBuilder tb = traverson.follow("href");
//        CollectionModel<EntityModel<Product>> products = traverson
//                .follow("productList")
//                .toObject(new CollectionModelType<EntityModel<Product>>(){});

        ParameterizedTypeReference<CollectionModel<EntityModel<Product>>> typeReference = new ParameterizedTypeReference<CollectionModel<EntityModel<Product>>>() {};
        CollectionModel<EntityModel<Product>> resProducts = tb.toObject(typeReference);
        Collection<EntityModel<Product>> products = resProducts.getContent();

        ArrayList<EntityModel<Product>> productsList = new ArrayList<>(products);

        List<Product> productsListNormal = productsList.
                stream().map(EntityModel::getContent).collect(Collectors.toList());
        //productsListNormal.forEach(System.out::println);
        
        model.addAttribute("products", productsList);


        return "testPage";
    }
}
