package pl.ske.project1.HATEOAS;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import pl.ske.project1.entity.Accused;
import pl.ske.project1.restservice.AccusedController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AccusedModelAssembler implements RepresentationModelAssembler<Accused, EntityModel<Accused>> {

    @Override
    public EntityModel<Accused> toModel(Accused accused) {
        EntityModel<Accused> accusedEntityModel = EntityModel.of(accused);

        Link selfLink = linkTo(methodOn(AccusedController.class).getAccusedById(accused.getId())).withSelfRel();
        Link allLink = linkTo(methodOn(AccusedController.class).getAllAccused()).withRel("allAccused");
        Link casesLink = linkTo(methodOn(AccusedController.class).getAccusedCases(accused.getId())).withRel("cases");
        accusedEntityModel.add(selfLink, allLink, casesLink);

        return accusedEntityModel;
    }

    @Override
    public CollectionModel<EntityModel<Accused>> toCollectionModel(Iterable<? extends Accused> entities) {

        List<Accused> accusedList = (List<Accused>) entities;
        List<EntityModel<Accused>> accusedEML = accusedList.stream().map(this::toModel).collect(Collectors.toList());

        Link selfLink = linkTo(methodOn(AccusedController.class).getAllAccused()).withSelfRel();

        return CollectionModel.of(accusedEML, selfLink);
    }
}
