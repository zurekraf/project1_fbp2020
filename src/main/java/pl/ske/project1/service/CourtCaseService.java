package pl.ske.project1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.ske.project1.entity.Charge;
import pl.ske.project1.entity.CourtCase;
import pl.ske.project1.entity.Product;
import pl.ske.project1.entity.Sentence;
import pl.ske.project1.repository.CourtCaseRepository;
import pl.ske.project1.repository.ProductRepository;

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

        //productRepository.deleteById(productId);
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

    /*
    public Optional<Hearing> replaceHearing(Hearing newHearing, Long hearingId) {
        return hearingRepository.findById(hearingId).map(hearing -> {
            //pierwszy to ten wyciągnięty z repo, 2gi to ten nowy
            hearing.setCourtcase(newHearing.getCourtcase());
            hearing.setCourtroom(newHearing.getCourtroom());
            hearing.setHearingDate(newHearing.getHearingDate());
            hearing.setPublic(newHearing.isPublic());

            return hearingRepository.save(hearing); //popodmienialiśmy i zapisujemy go na nowo
        });
    }
     */

}
