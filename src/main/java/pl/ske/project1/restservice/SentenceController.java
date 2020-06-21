package pl.ske.project1.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ske.project1.HATEOAS.SentenceModelAssembler;
import pl.ske.project1.entity.*;
import pl.ske.project1.service.SentenceService;

import java.util.List;
import java.util.Optional;

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
