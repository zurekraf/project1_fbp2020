package pl.ske.project1.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.ske.project1.DTO.DefenderDTO;
import pl.ske.project1.HATEOAS.DefenderModelAssembler;
import pl.ske.project1.entity.ApplicationUser;
import pl.ske.project1.entity.Defender;
import pl.ske.project1.service.DefenderService;
import pl.ske.project1.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/defenders")
public class DefenderController {
    @Autowired
    private DefenderService defenderService;
    @Autowired
    private DefenderModelAssembler defenderModelAssembler;
    @Autowired
    private UserService userService;

    @GetMapping(value = "/{id}", produces = "application/hal+json")
    public DefenderDTO getDefenderById(@PathVariable Long id) {
        Optional<Defender> defender = defenderService.findById(id);
        return defenderModelAssembler.toModel(defender.get());
    }

//    @GetMapping(value = "/{id}", produces = "application/hal+json")
//    public EntityModel<DefenderDTO> getDefenderById(@PathVariable Long id) {
//        Optional<Defender> defender = defenderService.findById(id);
//        DefenderDTO defenderDTO = modelMapper.map(defender.get(), DefenderDTO.class);
//        return defenderModelAssembler.toModel(defenderDTO);
//    }

    @GetMapping(value = "", produces = "application/hal+json")
    public CollectionModel<DefenderDTO> getAllDefenders() {
        List<Defender> defenderList = defenderService.findall();
        return defenderModelAssembler.toCollectionModel(defenderList);
    }

    // tylko dla admina
    @PutMapping(value = "/{defenderId}/user")
    public Defender replaceUserAccount(@RequestBody ApplicationUser applicationUser, @PathVariable Long defenderId) {
        Optional<Defender> defender = defenderService.findById(defenderId);
        defender.get().setApplicationUser(applicationUser);
        return defenderService.createDefender(defender.get());
    }

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @PostAuthorize("hasPermission(#id, 'updateDefender')")
    @PatchMapping(value = "/{id}")
    public ResponseEntity<Defender> updateDefender(@RequestBody Map<String, Object> updates, @PathVariable Long id) {
        Optional<Defender> updatedDefender = defenderService.updateDefender(updates, id);
        return ResponseEntity.of(updatedDefender);
    }

    /*
    @PatchMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody Map<String, Object> updates, @PathVariable Long id) {
        Optional<Product> updatedProduct = productService.updateProduct(updates, id);
        return ResponseEntity.of(updatedProduct);
    }
     */

//    @GetMapping(value = "", produces = "application/hal+json")
//    public CollectionModel<EntityModel<DefenderDTO>> getAllDefenders {
//        List<Defender> defenderList = defenderService.findall();
//        List<DefenderDTO> defenderDTOList = modelMapper.map(defenderList, List<DefenderDTO.class>.class);
//    }

}
