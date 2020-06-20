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
import pl.ske.project1.HATEOAS.SentenceModelAssembler;
import pl.ske.project1.entity.*;
import pl.ske.project1.service.AccusedService;
import pl.ske.project1.service.ChargeService;
import pl.ske.project1.service.CourtCaseService;
import pl.ske.project1.service.SentenceService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/sentences")
public class SentenceController {
    @Autowired
    private SentenceService sentenceService;
    @Autowired
    private SentenceModelAssembler sentenceModelAssembler;

    @GetMapping(value = "", produces = "application/hal+json")
    public CollectionModel<EntityModel<Sentence>> getAllSentences() {
        List<Sentence> sentencesList = sentenceService.findall();
        return sentenceModelAssembler.toCollectionModel(sentencesList);
    }

    @GetMapping(value = "/{id}", produces = "application/hal+json")
    public EntityModel<Sentence> getSentenceById(@PathVariable Long id) {
        Optional<Sentence> sentence = sentenceService.findById(id);
        return sentenceModelAssembler.toModel(sentence.get());
    }

}
