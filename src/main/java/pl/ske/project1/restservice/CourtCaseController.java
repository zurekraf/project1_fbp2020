package pl.ske.project1.restservice;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ske.project1.DTO.CourtCaseDTO;
import pl.ske.project1.entity.CourtCase;
import pl.ske.project1.entity.Defender;
import pl.ske.project1.service.CourtCaseService;
import pl.ske.project1.service.ProductService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cases")
public class CourtCaseController {
    @Autowired
    private CourtCaseService courtCaseService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "", produces = "application/json")
    public ResponseEntity<List<CourtCase>> getAllCases() {
        return ResponseEntity.ok(courtCaseService.findall());
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<CourtCase> getCaseById(@PathVariable Long id) {
        Optional<CourtCase> courtCase = courtCaseService.findById(id);

        //______________
//        ModelMapper modelMapper = new ModelMapper();
//        CourtCase courtCase1 = courtCase.get();
//        CourtCaseDTO courtCaseDTO = modelMapper.map(courtCase1, CourtCaseDTO.class);
//        System.out.println(courtCaseDTO.getId()+" "+courtCaseDTO.getCaseCode()+" "+ courtCaseDTO.getAccusedId());
//        System.out.println(courtCaseDTO.getSentenceId());


        //______________

        return ResponseEntity.ok(courtCase.get()); //poleci 500 jeśli podamy id którego nie ma
    }
}
