package pl.ske.project1.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ske.project1.entity.Accused;
import pl.ske.project1.entity.CourtCase;
import pl.ske.project1.service.AccusedService;
import pl.ske.project1.service.CourtCaseService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/accused")
public class AccusedController {
    @Autowired
    private AccusedService accusedService;

    @GetMapping(value = "", produces = "application/json")
    public ResponseEntity<List<Accused>> getAllAccused() {
        return ResponseEntity.ok(accusedService.findall());
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Accused> getAccusedById(@PathVariable Long id) {
        Optional<Accused> accused = accusedService.findById(id);
        return ResponseEntity.ok(accused.get()); //poleci 500 jeśli podamy id którego nie ma
    }
}
