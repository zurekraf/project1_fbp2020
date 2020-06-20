package pl.ske.project1.HATEOAS;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import pl.ske.project1.DTO.DefenderDTO;
import pl.ske.project1.entity.Charge;
import pl.ske.project1.entity.Defender;
import pl.ske.project1.restservice.ChargeController;
import pl.ske.project1.restservice.DefenderController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DefenderModelAssembler implements RepresentationModelAssembler<Defender, DefenderDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public DefenderDTO toModel(Defender defender) {
        DefenderDTO defenderDTO = modelMapper.map(defender, DefenderDTO.class);
        Link selfLink = linkTo(methodOn(DefenderController.class).getDefenderById(defenderDTO.getId())).withSelfRel();
        Link allLink = linkTo(methodOn(DefenderController.class).getAllDefenders()).withRel("allDefenders");
        defenderDTO.add(selfLink, allLink);

        return defenderDTO;

//        EntityModel<DefenderDTO> defenderDTOEntityModel = EntityModel.of(defenderDTO);
//        Link selfLink = linkTo(methodOn(DefenderController.class).getDefenderById(defenderDTO.getId())).withSelfRel();
//        Link allLink = linkTo(methodOn(DefenderController.class).getAllDefenders()).withRel("allDefenders");
//        defenderDTOEntityModel.add(selfLink, allLink);
//        return defenderDTOEntityModel;
    }

    @Override
    public CollectionModel<DefenderDTO> toCollectionModel(Iterable<? extends Defender> defendersList) {
        List<DefenderDTO> defenderDTOS = new ArrayList<>();

        for(Defender defender : defendersList) {
            DefenderDTO defenderDTO = modelMapper.map(defender, DefenderDTO.class);
            Link selfLink = linkTo(methodOn(DefenderController.class).getDefenderById(defenderDTO.getId())).withSelfRel();
            Link allLink = linkTo(methodOn(DefenderController.class).getAllDefenders()).withRel("allDefenders");
            defenderDTO.add(selfLink, allLink);
            defenderDTOS.add(defenderDTO);
        }
        Link selfLink = linkTo(methodOn(DefenderController.class).getAllDefenders()).withSelfRel();
        return CollectionModel.of(defenderDTOS, selfLink);

//        List<DefenderDTO> defendersList = (List<DefenderDTO>) entities;
//        List<EntityModel<DefenderDTO>> defenderEML = defendersList.stream().map(this::toModel).collect(Collectors.toList());
//
//        Link selfLink = linkTo(methodOn(DefenderController.class).getAllDefenders()).withSelfRel();
//
//        return CollectionModel.of(defenderEML, selfLink);
    }
}
