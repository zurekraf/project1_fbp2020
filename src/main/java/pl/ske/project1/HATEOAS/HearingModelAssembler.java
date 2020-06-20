package pl.ske.project1.HATEOAS;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import pl.ske.project1.DTO.DefenderDTO;
import pl.ske.project1.DTO.HearingDTO;
import pl.ske.project1.entity.*;
import pl.ske.project1.restservice.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class HearingModelAssembler implements RepresentationModelAssembler<Hearing, HearingDTO> {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public HearingDTO toModel(Hearing hearing) {
        HearingDTO hearingDTO = modelMapper.map(hearing, HearingDTO.class);

        Link selfLink = linkTo(methodOn(HearingController.class).getHearingById(hearing.getId())).withSelfRel();
        Link allLink = linkTo(methodOn(HearingController.class).getAllHearings()).withRel("allHearings");

        //link do case

        hearingDTO.add(selfLink, allLink);
        return hearingDTO;
    }

    @Override
    public CollectionModel<HearingDTO> toCollectionModel(Iterable<? extends Hearing> hearingsList) {
        List<HearingDTO> hearingDTOS = new ArrayList<>();

        for(Hearing hearing : hearingsList) {
            HearingDTO hearingDTO = this.toModel(hearing);
            hearingDTOS.add(hearingDTO);
        }
        Link selfLink = linkTo(methodOn(HearingController.class).getAllHearings()).withSelfRel();
        return CollectionModel.of(hearingDTOS, selfLink);

//        List<Hearing> hearingsList = (List<Hearing>) entities;
//        List<EntityModel<Hearing>> hearingEML = hearingsList.stream().map(this::toModel).collect(Collectors.toList());
//
//        Link selfLink = linkTo(methodOn(HearingController.class).getAllHearings()).withSelfRel();
//
//        return CollectionModel.of(hearingEML, selfLink);
    }
}

//    @Override
//    public CollectionModel<DefenderDTO> toCollectionModel(Iterable<? extends Defender> defendersList) {
//        List<DefenderDTO> defenderDTOS = new ArrayList<>();
//
//        for(Defender defender : defendersList) {
//            DefenderDTO defenderDTO = modelMapper.map(defender, DefenderDTO.class);
//            Link selfLink = linkTo(methodOn(DefenderController.class).getDefenderById(defenderDTO.getId())).withSelfRel();
//            Link allLink = linkTo(methodOn(DefenderController.class).getAllDefenders()).withRel("allDefenders");
//            defenderDTO.add(selfLink, allLink);
//            defenderDTOS.add(defenderDTO);
//        }
//        Link selfLink = linkTo(methodOn(DefenderController.class).getAllDefenders()).withSelfRel();
//        return CollectionModel.of(defenderDTOS, selfLink);
//    }
//}


