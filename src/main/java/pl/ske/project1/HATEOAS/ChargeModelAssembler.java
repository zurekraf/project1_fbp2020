package pl.ske.project1.HATEOAS;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import pl.ske.project1.entity.Accused;
import pl.ske.project1.entity.Charge;
import pl.ske.project1.restservice.AccusedController;
import pl.ske.project1.restservice.ChargeController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ChargeModelAssembler implements RepresentationModelAssembler<Charge, EntityModel<Charge>> {

    @Override
    public EntityModel<Charge> toModel(Charge charge) {
        EntityModel<Charge> chargeEntityModel = EntityModel.of(charge);

        Link selfLink = linkTo(methodOn(ChargeController.class).getChargeById(charge.getId())).withSelfRel();
        Link allLink = linkTo(methodOn(ChargeController.class).getAllCharges()).withRel("allCharges");
        chargeEntityModel.add(selfLink, allLink);

        return chargeEntityModel;
    }

    @Override
    public CollectionModel<EntityModel<Charge>> toCollectionModel(Iterable<? extends Charge> entities) {

        List<Charge> chargesList = (List<Charge>) entities;
        List<EntityModel<Charge>> chargeEML = chargesList.stream().map(this::toModel).collect(Collectors.toList());

        Link selfLink = linkTo(methodOn(ChargeController.class).getAllCharges()).withSelfRel();

        return CollectionModel.of(chargeEML, selfLink);
    }
}
