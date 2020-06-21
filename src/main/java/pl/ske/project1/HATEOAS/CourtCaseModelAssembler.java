package pl.ske.project1.HATEOAS;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import pl.ske.project1.DTO.CourtCaseDTO;
import pl.ske.project1.DTO.DefenderDTO;
import pl.ske.project1.entity.CourtCase;
import pl.ske.project1.entity.Defender;
import pl.ske.project1.restservice.CourtCaseController;
import pl.ske.project1.restservice.DefenderController;
import pl.ske.project1.restservice.SentenceController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CourtCaseModelAssembler implements RepresentationModelAssembler<CourtCase, CourtCaseDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CourtCaseDTO toModel(CourtCase courtCase) {
        CourtCaseDTO courtCaseDTO = modelMapper.map(courtCase, CourtCaseDTO.class);
        Link selfLink = linkTo(methodOn(CourtCaseController.class).getCaseById(courtCaseDTO.getId())).withSelfRel();
        Link allLink = linkTo(methodOn(CourtCaseController.class).getAllCases()).withRel("allCases");
        Link chargesLink = linkTo(methodOn(CourtCaseController.class).getCaseCharges(courtCaseDTO.getId())).withRel("caseCharges");
        Link hearingsLink = linkTo(methodOn(CourtCaseController.class).getCaseHearings(courtCaseDTO.getId())).withRel("caseHearings");

        courtCaseDTO.add(selfLink, allLink, chargesLink, hearingsLink);

        if(courtCase.getSentence() != null) {
            Link sentenceLink = linkTo(methodOn(CourtCaseController.class).getCaseSentence(courtCaseDTO.getSentenceId())).withRel("caseSentence");
            courtCaseDTO.add(sentenceLink);
        }
        return courtCaseDTO;
    }

    @Override
    public CollectionModel<CourtCaseDTO> toCollectionModel(Iterable<? extends CourtCase> casesList) {
        List<CourtCaseDTO> courtCaseDTOS = new ArrayList<>();

        for(CourtCase courtCase : casesList) {
            CourtCaseDTO courtCaseDTO = this.toModel(courtCase);
            courtCaseDTOS.add(courtCaseDTO);
        }
        Link selfLink = linkTo(methodOn(CourtCaseController.class).getAllCases()).withSelfRel();
        return CollectionModel.of(courtCaseDTOS, selfLink);
    }
}
