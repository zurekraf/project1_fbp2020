package pl.ske.project1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.ske.project1.entity.Charge;
import pl.ske.project1.entity.CourtCase;
import pl.ske.project1.entity.Sentence;
import pl.ske.project1.repository.CourtCaseRepository;

import java.util.List;
import java.util.Optional;

@Service
@Component
public class CourtCaseService {
    @Autowired
    private CourtCaseRepository courtCaseRepository;
    @Autowired
    private ChargeService chargeService;
    @Autowired
    private SentenceService sentenceService;

    public List<CourtCase> findall() {
        return (List<CourtCase>) courtCaseRepository.findAll();
    }

    public Optional<CourtCase> findById(Long courtCaseId) {
        return courtCaseRepository.findById(courtCaseId);
    }

    public void deleteChargeById(Long courtCaseId, Long chargeId) {

        Optional<CourtCase> courtCase = courtCaseRepository.findById(courtCaseId);
        Optional<Charge> charge = chargeService.findById(chargeId);
        courtCase.get().getCharges().remove(charge.get());

        courtCaseRepository.save(courtCase.get());
    }

    public Charge addCharge(Long courtCaseId, Charge newCharge) {
        Optional<Charge> charge = chargeService.findById(newCharge.getId());
        Optional<CourtCase> courtCase = courtCaseRepository.findById(courtCaseId);
        courtCase.get().getCharges().add(charge.get());
        courtCaseRepository.save(courtCase.get());

        return newCharge;
    }

    public CourtCase createCourtCase(CourtCase newCourtCase) {
        return courtCaseRepository.save(newCourtCase);
    }

    public CourtCase sentencing(Long courtCaseId, Sentence sentence) {
//        Sentence newSentence = sentenceService.createSentence(sentence);
//        Optional<CourtCase> courtCase = courtCaseRepository.findById(courtCaseId);
//        courtCase.get().setSentence(newSentence);
//        return courtCaseRepository.save(courtCase.get());
        Optional<CourtCase> courtCase = courtCaseRepository.findById(courtCaseId);
        if(courtCase.get().getSentence() == null) {
            courtCase.get().setSentence(sentence);
        } else {
            Optional<Sentence> currentSentence = sentenceService.findById(courtCase.get().getSentence().getId());
            currentSentence.get().setDescription(sentence.getDescription());
            currentSentence.get().setPossibilityOfParole(sentence.isPossibilityOfParole());
            currentSentence.get().setTimeFrame(sentence.getTimeFrame());
            sentenceService.createSentence(currentSentence.get());
        }
        return courtCaseRepository.save(courtCase.get());
    }
}
