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

@Controller
public class MainController {


    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    @GetMapping("/index2")
    public String test22(Model model) {

        /*
        String uri = "http://localhost:8080/api/defenders";
        Traverson traverson = new Traverson(URI.create(uri), MediaTypes.HAL_JSON);
        Traverson.TraversalBuilder tb = traverson.follow("href");

        ParameterizedTypeReference<CollectionModel<DefenderDTO>> typeReference = new ParameterizedTypeReference<CollectionModel<DefenderDTO>>() {};
        CollectionModel<DefenderDTO> resDefenders = tb.toObject(typeReference);
        Collection<DefenderDTO> defenders = resDefenders.getContent();
        ArrayList<DefenderDTO> defendersList = new ArrayList<>(defenders);

        defendersList.forEach(System.out::println);

        for(DefenderDTO x : defendersList) {
            System.out.println(x);
            System.out.println(x.getLinks());
        }
        model.addAttribute("defenders", defendersList);
         */
        return "testPage";
    }
}
