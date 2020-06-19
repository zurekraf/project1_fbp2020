package pl.ske.project1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.ske.project1.entity.CourtCase;
import pl.ske.project1.entity.Product;
import pl.ske.project1.repository.CourtCaseRepository;
import pl.ske.project1.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@Component
public class CourtCaseService {
    @Autowired
    private CourtCaseRepository courtCaseRepository;

    public List<CourtCase> findall() {
        return (List<CourtCase>) courtCaseRepository.findAll();
    }

    public Optional<CourtCase> findById(Long courtCaseId) {
        return courtCaseRepository.findById(courtCaseId);
    }

}
