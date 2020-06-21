package pl.ske.project1.HATEOAS;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import pl.ske.project1.entity.Sentence;
import pl.ske.project1.restservice.SentenceController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SentenceModelAssembler implements RepresentationModelAssembler<Sentence, EntityModel<Sentence>> {

    @Override
    public EntityModel<Sentence> toModel(Sentence sentence) {
        EntityModel<Sentence> sentenceEntityModel = EntityModel.of(sentence);

        Link selfLink = linkTo(methodOn(SentenceController.class).getSentenceById(sentence.getId())).withSelfRel();
        Link allLink = linkTo(methodOn(SentenceController.class).getAllSentences()).withRel("allSentences");
        sentenceEntityModel.add(selfLink, allLink);

        return sentenceEntityModel;
    }

    @Override
    public CollectionModel<EntityModel<Sentence>> toCollectionModel(Iterable<? extends Sentence> entities) {

        List<Sentence> sentencesList = (List<Sentence>) entities;
        List<EntityModel<Sentence>> sentenceEML = sentencesList.stream().map(this::toModel).collect(Collectors.toList());

        Link selfLink = linkTo(methodOn(SentenceController.class).getAllSentences()).withSelfRel();

        return CollectionModel.of(sentenceEML, selfLink);
    }
}

