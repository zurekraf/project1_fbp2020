package pl.ske.project1.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ske.project1.HATEOAS.AccusedModelAssembler;
import pl.ske.project1.HATEOAS.ChargeModelAssembler;
import pl.ske.project1.entity.Accused;
import pl.ske.project1.entity.Charge;
import pl.ske.project1.entity.CourtCase;
import pl.ske.project1.entity.Product;
import pl.ske.project1.service.AccusedService;
import pl.ske.project1.service.ChargeService;
import pl.ske.project1.service.CourtCaseService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/charges")
public class ChargeController {
    @Autowired
    private ChargeService chargeService;
    @Autowired
    private ChargeModelAssembler chargeModelAssembler;

//    @GetMapping(value = "", produces = "application/hal+json")
//    public CollectionModel<EntityModel<Charge>> getAllCharges() {
//        List<Charge> chargesList = chargeService.findall();
//        return chargeModelAssembler.toCollectionModel(chargesList);
//    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping(value = "", produces = "application/hal+json")
    public CollectionModel<EntityModel<Charge>> getAllCharges() {
        List<Charge> chargesList = chargeService.findall();
        List<EntityModel<Charge>> chargeEML = chargesList.stream().map(chargeModelAssembler::toModel).collect(Collectors.toList());
        Link selfLink = linkTo(methodOn(ChargeController.class).getAllCharges()).withSelfRel();

        return CollectionModel.of(chargeEML, selfLink);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping(value = "/{id}", produces = "application/hal+json")
    public EntityModel<Charge> getChargeById(@PathVariable Long id) {
        Optional<Charge> charge = chargeService.findById(id);
        return chargeModelAssembler.toModel(charge.get());
    }

}

//    List<Charge> chargesList = (List<Charge>) entities;
//    List<EntityModel<Charge>> chargeEML = chargesList.stream().map(this::toModel).collect(Collectors.toList());
//
//    Link selfLink = linkTo(methodOn(ChargeController.class).getAllCharges()).withSelfRel();
//
//        return CollectionModel.of(chargeEML, selfLink);
