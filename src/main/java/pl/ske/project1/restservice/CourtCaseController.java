package pl.ske.project1.restservice;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ske.project1.DTO.CourtCaseDTO;
import pl.ske.project1.DTO.DefenderDTO;
import pl.ske.project1.HATEOAS.ChargeModelAssembler;
import pl.ske.project1.HATEOAS.CourtCaseModelAssembler;
import pl.ske.project1.entity.Accused;
import pl.ske.project1.entity.Charge;
import pl.ske.project1.entity.CourtCase;
import pl.ske.project1.entity.Defender;
import pl.ske.project1.service.CourtCaseService;
import pl.ske.project1.service.ProductService;

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

    @GetMapping(value = "", produces = "application/hal+json")
    public CollectionModel<CourtCaseDTO> getAllCases() {
        List<CourtCase> casesList = courtCaseService.findall();
        return courtCaseModelAssembler.toCollectionModel(casesList);
    }

    @GetMapping(value = "/{id}", produces = "application/hal+json")
    public CourtCaseDTO getCaseById(@PathVariable Long id) {
        Optional<CourtCase> courtCase = courtCaseService.findById(id);
        return courtCaseModelAssembler.toModel(courtCase.get());
    }

    @GetMapping(value = "/{id}/charges", produces = "application/json")
    public CollectionModel<EntityModel<Charge>> getCaseCharges(@PathVariable Long id) {
        Optional<CourtCase> courtCase = courtCaseService.findById(id);

        Set<Charge> chargesSet = courtCase.get().getCharges();

        System.out.println(chargesSet.size());

        List<Charge> chargesList = new ArrayList<>(chargesSet);

        System.out.println(chargesList.size()); //??????????????????????????/

        CollectionModel<EntityModel<Charge>> chargesCollectionModel = chargeModelAssembler.toCollectionModel(chargesList);
//        CollectionModel<EntityModel<Charge>> chargesCollectionModel = chargeModelAssembler.toCollectionModel(courtCase.get().getCharges());

        return chargesCollectionModel;
    }

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
