package pl.ske.project1.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ske.project1.entity.Defender;
import pl.ske.project1.service.DefenderService;
import pl.ske.project1.service.ProductService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/defenders")
public class DefenderController {
    @Autowired
    private DefenderService defenderService;

    @GetMapping(value = "", produces = "application/json")
    public ResponseEntity<List<Defender>> getAllDefenders() {
        return ResponseEntity.ok(defenderService.findall());
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Defender> getProductById(@PathVariable Long id) {
        Optional<Defender> defender = defenderService.findById(id);
        return ResponseEntity.ok(defender.get()); //poleci 500 jeśli podamy id którego nie ma
    }
}
