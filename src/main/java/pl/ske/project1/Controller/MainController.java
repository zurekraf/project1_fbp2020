package pl.ske.project1.Controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.*;
import org.springframework.hateoas.client.Traverson;
import org.springframework.hateoas.server.core.TypeReferences;
import org.springframework.hateoas.server.core.TypeReferences.CollectionModelType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import pl.ske.project1.entity.Product;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)



    @GetMapping("/index")
    public String test(Model model) {

        String uri = "http://localhost:8080/api/products/all";
        RestTemplate restTemplate = new RestTemplate();

        Link link1 = linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel();

                //builder z default cookie

//        WebClient.Builder builder = WebClient.builder(); //to do beana można wyrzucić
//        Product[] products = builder.build()
//                .get() //albo .post
//                .uri(link1.toUri())
//                .retrieve()
//                .bodyToMono(Product[].class)
//                .block();
        //mono to takie promise w javie bo to asynchronicznie idzie
        //block czeka aż mono do nas wróci. Czyli takie blokowanie.


//        //to chyba najdalej
//        WebClient.Builder builder = WebClient.builder(); //to do beana można wyrzucić
//        List<Product> test11 = (List<Product>) builder.build()
//                .get() //albo .post
//                .uri(link1.toUri())
//                .retrieve()
//                .bodyToFlux(List.class);



        //test11.subscribe(System.out::println);

//        List<Product> test22 = test11.collectList().block();
//        test22.forEach(System.out::println);
//        System.out.println(test22.size());
//        System.out.println(test22.get(0).getName());

//                .bodyToFlux(Product[].class)
//                .collectList();




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

//        Links lol = resProducts.getLinks();
//        System.out.println(lol);

        ArrayList<EntityModel<Product>> productsList = new ArrayList<>(products);
        //productsList.forEach(System.out::println);


        List<Product> productsListNormal = productsList.
                stream().map(EntityModel::getContent).collect(Collectors.toList());
        //productsListNormal.forEach(System.out::println);
        
        model.addAttribute("products", productsList);

//        Product x = newList.get(0).getContent();
//        List<Link> z = newList.get(0).getLinks().toList();
//        System.out.println(z.get(0).);


//        return "testPage";
        return "lol";
    }

    @PostMapping("/secondtest")
    public String secondTest(@RequestParam("username") String username) {

        System.out.println(username);

        return "redirect:/index";
    }

}
