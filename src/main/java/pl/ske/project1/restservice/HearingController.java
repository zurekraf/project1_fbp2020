package pl.ske.project1.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ske.project1.DTO.HearingDTO;
import pl.ske.project1.HATEOAS.HearingModelAssembler;
import pl.ske.project1.HATEOAS.SentenceModelAssembler;
import pl.ske.project1.entity.Hearing;
import pl.ske.project1.entity.Sentence;
import pl.ske.project1.service.HearingService;
import pl.ske.project1.service.SentenceService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/hearings")
public class HearingController {
    @Autowired
    private HearingService hearingService;
    @Autowired
    private HearingModelAssembler hearingModelAssembler;

    @GetMapping(value = "", produces = "application/hal+json")
    public CollectionModel<HearingDTO> getAllHearings() {
        List<Hearing> hearingsList = hearingService.findall();
        return hearingModelAssembler.toCollectionModel(hearingsList);
    }

    @GetMapping(value = "/{id}", produces = "application/hal+json")
    public HearingDTO getHearingById(@PathVariable Long id) {
        Optional<Hearing> hearing = hearingService.findById(id);
        return hearingModelAssembler.toModel(hearing.get());

    }

}