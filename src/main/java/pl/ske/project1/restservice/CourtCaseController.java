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
    private ModelMapper modelMapper;
    @Autowired
    private SentenceModelAssembler sentenceModelAssembler;

    @PreAuthorize("hasAnyAuthority('JUDGE', 'PROSECUTOR', 'DEFENDER')")
    @GetMapping(value = "", produces = "application/hal+json")
    public CollectionModel<CourtCaseDTO> getAllCases() {
        List<CourtCase> casesList = courtCaseService.findall();
        return courtCaseModelAssembler.toCollectionModel(casesList);
    }

    @PreAuthorize("hasAnyAuthority('JUDGE', 'PROSECUTOR', 'DEFENDER')")
    @GetMapping(value = "/{id}", produces = "application/hal+json")
    public CourtCaseDTO getCaseById(@PathVariable Long id) {
        Optional<CourtCase> courtCase = courtCaseService.findById(id);
        return courtCaseModelAssembler.toModel(courtCase.get());
    }

    @GetMapping(value = "/{id}/charges", produces = "application/json")
    public CollectionModel<EntityModel<Charge>> getCaseCharges(@PathVariable Long id) {
        Optional<CourtCase> courtCase = courtCaseService.findById(id);

        Set<Charge> chargesSet = courtCase.get().getCharges();
        List<Charge> chargesList = new ArrayList<>(chargesSet);

        CollectionModel<EntityModel<Charge>> chargesCollectionModel = chargeModelAssembler.toCollectionModel(chargesList);

        return chargesCollectionModel;
    }
    @GetMapping(value = "/{id}/hearings", produces = "application/json")
    public CollectionModel<HearingDTO> getCaseHearings(@PathVariable Long id) {
        Optional<CourtCase> courtCase = courtCaseService.findById(id);
        List<Hearing> hearingsList = new ArrayList<>(courtCase.get().getHearings());
        CollectionModel<HearingDTO> hearingsDTOCollectionModel = hearingModelAssembler.toCollectionModel(hearingsList);
        return hearingsDTOCollectionModel;
    }

    @PreAuthorize("hasAnyAuthority('JUDGE')")
    @PostMapping("/{id}/hearings")
    public Hearing newHearing(@RequestBody Hearing newHearing) {
        //przetestować z @PostAttribute Hearing

        return hearingService.createHearing(newHearing);
    }

    @PreAuthorize("hasAnyAuthority('JUDGE')")
    @PutMapping("/{id}/hearings/{hearingId}")
    public ResponseEntity<Hearing> replaceHearing(@RequestBody Hearing updatedHearing, @PathVariable Long hearingId) {
        Optional<Hearing> replacedProduct = hearingService.replaceHearing(updatedHearing, hearingId);

        return ResponseEntity.of(replacedProduct);
    }

    @PreAuthorize("hasAnyAuthority('PROSECUTOR')") //zmienić na prosecutor!!!!!
    @DeleteMapping("/{caseId}/charges/{chargeId}")
    public void deleteProduct(@PathVariable Long caseId, @PathVariable Long chargeId) {
        courtCaseService.deleteChargeById(caseId, chargeId);
//        productService.deleteById(id);
    }

    //jeszcze nie działa
    @PreAuthorize("hasAnyAuthority('JUDGE')")
    @GetMapping("/{caseId}/sentence")
    public EntityModel<Sentence> getCaseSentence(@PathVariable Long caseId) {
        Optional<CourtCase> courtCase = courtCaseService.findById(caseId);
        Sentence sentence = courtCase.get().getSentence();
        return sentenceModelAssembler.toModel(sentence);
    }

    @PreAuthorize("hasAnyAuthority('JUDGE')")
    @PutMapping("/{caseId}/sentence")
    public CourtCaseDTO sentencing(@RequestBody Sentence sentence, @PathVariable Long caseId) {
        CourtCase courtCase = courtCaseService.sentencing(caseId, sentence);
        return courtCaseModelAssembler.toModel(courtCase);
    }




    //z produktu
//    @PutMapping("/{id}")
//    public ResponseEntity<Product> replaceProduct(@RequestBody Product product, @PathVariable Long id) {
//        Optional<Product> replacedProduct = productService.replaceProduct(product, id);
//        return ResponseEntity.of(replacedProduct);
//    }

//    @PreAuthorize("hasAnyAuthority('JUDGE')")
//    @PostMapping("/{id}/hearings")
//    public Hearing newHearing(@RequestBody Hearing newHearing) {
//        return hearingService.createHearing(newHearing);
//    }

//    @GetMapping(value = "", produces = "application/json")
//    public ResponseEntity<List<CourtCase>> getAllCases() {
//        return ResponseEntity.ok(courtCaseService.findall());
//    }
//
//    @GetMapping(value = "/{id}", produces = "application/json")
//    public ResponseEntity<CourtCase> getCaseById(@PathVariable Long id) {
//        Optional<CourtCase> courtCase = courtCaseService.findById(id);
//
//        return ResponseEntity.ok(courtCase.get()); //poleci 500 jeśli podamy id którego nie ma
//    }
}
