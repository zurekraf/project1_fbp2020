package pl.ske.project1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.ske.project1.entity.Accused;
import pl.ske.project1.entity.Sentence;
import pl.ske.project1.repository.AccusedRepository;
import pl.ske.project1.repository.SentenceRepository;

import java.util.List;
import java.util.Optional;

@Service
@Component
public class SentenceService {
    @Autowired
    private SentenceRepository sentenceRepository;

    public List<Sentence> findall() {
        return (List<Sentence>) sentenceRepository.findAll();
    }

    public Optional<Sentence> findById(Long sentenceId) {
        return sentenceRepository.findById(sentenceId);
    }

}
