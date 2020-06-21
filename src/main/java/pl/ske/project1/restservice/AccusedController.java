package pl.ske.project1.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ske.project1.DTO.CourtCaseDTO;
import pl.ske.project1.HATEOAS.AccusedModelAssembler;
import pl.ske.project1.HATEOAS.CourtCaseModelAssembler;
import pl.ske.project1.entity.Accused;
import pl.ske.project1.entity.CourtCase;
import pl.ske.project1.service.AccusedService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/accused")
public class AccusedController {
    @Autowired
    private AccusedService accusedService;
    @Autowired
    private AccusedModelAssembler accusedModelAssembler;
    @Autowired
    private CourtCaseModelAssembler courtCaseModelAssembler;

    @PreAuthorize("hasAnyAuthority('JUDGE', 'ADMIN', 'PROSECUTOR', 'DEFENDER')")
    @GetMapping(value = "/{id}", produces = "application/hal+json")
    public EntityModel<Accused> getAccusedById(@PathVariable Long id) {
        Optional<Accused> accused = accusedService.findById(id);
        return accusedModelAssembler.toModel(accused.get());
    }

    @PreAuthorize("hasAnyAuthority('JUDGE', 'ADMIN', 'PROSECUTOR')")
    @GetMapping(value = "", produces = "application/hal+json")
    public CollectionModel<EntityModel<Accused>> getAllAccused() {
        List<Accused> accusedList = accusedService.findall();
        return accusedModelAssembler.toCollectionModel(accusedList);
    }

    @PreAuthorize("hasAnyAuthority('JUDGE', 'ADMIN', 'PROSECUTOR', 'DEFENDER')")
    @GetMapping(value = "/{id}/cases", produces = "application/json")
    public CollectionModel<CourtCaseDTO> getAccusedCases(@PathVariable Long id) {
        Optional<Accused> accused = accusedService.findById(id);
        List<CourtCase> courtCaseList = new ArrayList<>(accused.get().getCases());
        return courtCaseModelAssembler.toCollectionModel(courtCaseList);
    }
}
