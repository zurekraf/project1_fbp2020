package pl.ske.project1.restservice;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.ske.project1.DTO.CourtCaseDTO;
import pl.ske.project1.DTO.HearingDTO;
import pl.ske.project1.HATEOAS.ChargeModelAssembler;
import pl.ske.project1.HATEOAS.CourtCaseModelAssembler;
import pl.ske.project1.HATEOAS.HearingModelAssembler;
import pl.ske.project1.HATEOAS.SentenceModelAssembler;
import pl.ske.project1.entity.*;
import pl.ske.project1.service.CourtCaseService;
import pl.ske.project1.service.HearingService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/cases")
public class CourtCaseController {
    @Autowired
    private CourtCaseService courtCaseService;
    @Autowired
    private ChargeModelAssembler chargeModelAssembler;
    @Autowired
    private CourtCaseModelAssembler courtCaseModelAssembler;
    @Autowired
    private HearingModelAssembler hearingModelAssembler;
    @Autowired
    private HearingService hearingService;
    @Autowired
    private SentenceModelAssembler sentenceModelAssembler;

    @PreAuthorize("hasAnyAuthority('JUDGE', 'ADMIN', 'PROSECUTOR', 'DEFENDER')")
    @GetMapping(value = "", produces = "application/hal+json")
    public CollectionModel<CourtCaseDTO> getAllCases() {
        List<CourtCase> casesList = courtCaseService.findall();
        return courtCaseModelAssembler.toCollectionModel(casesList);
    }

    @PreAuthorize("hasAnyAuthority('JUDGE', 'ADMIN', 'PROSECUTOR', 'DEFENDER')")
    @GetMapping(value = "/{id}", produces = "application/hal+json")
    public CourtCaseDTO getCaseById(@PathVariable Long id) {
        Optional<CourtCase> courtCase = courtCaseService.findById(id);
        return courtCaseModelAssembler.toModel(courtCase.get());
    }

    @PreAuthorize("hasAnyAuthority('JUDGE', 'ADMIN', 'PROSECUTOR', 'DEFENDER')")
    @GetMapping(value = "/{id}/charges", produces = "application/json")
    public CollectionModel<EntityModel<Charge>> getCaseCharges(@PathVariable Long id) {
        Optional<CourtCase> courtCase = courtCaseService.findById(id);
        Set<Charge> chargesSet = courtCase.get().getCharges();
        List<Charge> chargesList = new ArrayList<>(chargesSet);
        CollectionModel<EntityModel<Charge>> chargesCollectionModel = chargeModelAssembler.toCollectionModel(chargesList);

        return chargesCollectionModel;
    }

    @PreAuthorize("hasAnyAuthority('JUDGE', 'ADMIN', 'PROSECUTOR', 'DEFENDER')")
    @GetMapping(value = "/{id}/hearings", produces = "application/json")
    public CollectionModel<HearingDTO> getCaseHearings(@PathVariable Long id) {
        Optional<CourtCase> courtCase = courtCaseService.findById(id);
        List<Hearing> hearingsList = new ArrayList<>(courtCase.get().getHearings());
        CollectionModel<HearingDTO> hearingsDTOCollectionModel = hearingModelAssembler.toCollectionModel(hearingsList);
        return hearingsDTOCollectionModel;
    }

    @PreAuthorize("hasAnyAuthority('JUDGE', 'ADMIN')")
    @PostMapping("/{id}/hearings")
    public Hearing newHearing(@RequestBody Hearing newHearing) {
        return hearingService.createHearing(newHearing);
    }

    @PreAuthorize("hasAnyAuthority('JUDGE', 'ADMIN')")
    @PutMapping("/{id}/hearings/{hearingId}")
    public ResponseEntity<Hearing> replaceHearing(@RequestBody Hearing updatedHearing, @PathVariable Long hearingId) {
        Optional<Hearing> replacedProduct = hearingService.replaceHearing(updatedHearing, hearingId);

        return ResponseEntity.of(replacedProduct);
    }

    @PreAuthorize("hasAnyAuthority('PROSECUTOR', 'ADMIN')")
    @DeleteMapping("/{caseId}/charges/{chargeId}")
    public void deleteCharge(@PathVariable Long caseId, @PathVariable Long chargeId) {
        courtCaseService.deleteChargeById(caseId, chargeId);
    }

    @PreAuthorize("hasAnyAuthority('JUDGE', 'ADMIN', 'PROSECUTOR', 'DEFENDER')")
    @GetMapping("/{caseId}/sentence")
    public EntityModel<Sentence> getCaseSentence(@PathVariable Long caseId) {
        Optional<CourtCase> courtCase = courtCaseService.findById(caseId);
        Sentence sentence = courtCase.get().getSentence();
        return sentenceModelAssembler.toModel(sentence);
    }

    @PreAuthorize("hasAnyAuthority('JUDGE', 'ADMIN')")
    @PutMapping("/{caseId}/sentence")
    public CourtCaseDTO sentencing(@RequestBody Sentence sentence, @PathVariable Long caseId) {
        CourtCase courtCase = courtCaseService.sentencing(caseId, sentence);
        return courtCaseModelAssembler.toModel(courtCase);
    }

    @PreAuthorize("hasAnyAuthority('JUDGE', 'ADMIN', 'PROSECUTOR')")
    @PostMapping("")
    public CourtCaseDTO addCourtCase(@RequestBody CourtCase courtCase) {
        CourtCase newCourtCase = courtCaseService.createCourtCase(courtCase);
        return courtCaseModelAssembler.toModel(newCourtCase);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'PROSECUTOR')")
    @PatchMapping("/{caseId}/charges")
    public Charge addCharge(@RequestBody Charge charge, @PathVariable Long caseId) {
        return courtCaseService.addCharge(caseId, charge);
    }
}
