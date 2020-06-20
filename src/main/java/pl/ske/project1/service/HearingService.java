package pl.ske.project1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.ske.project1.entity.Hearing;
import pl.ske.project1.entity.Sentence;
import pl.ske.project1.repository.HearingRepository;
import pl.ske.project1.repository.SentenceRepository;

import java.util.List;
import java.util.Optional;

@Service
@Component
public class HearingService {
    @Autowired
    private HearingRepository hearingRepository;

    public List<Hearing> findall() {
        return (List<Hearing>) hearingRepository.findAll();
    }

    public Optional<Hearing> findById(Long hearingId) {
        return hearingRepository.findById(hearingId);
    }

}
