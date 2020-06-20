package pl.ske.project1.restservice;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ske.project1.DTO.DefenderDTO;
import pl.ske.project1.HATEOAS.DefenderModelAssembler;
import pl.ske.project1.entity.Charge;
import pl.ske.project1.entity.Defender;
import pl.ske.project1.service.DefenderService;
import pl.ske.project1.service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/defenders")
public class DefenderController {
    @Autowired
    private DefenderService defenderService;
    @Autowired
    private DefenderModelAssembler defenderModelAssembler;

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

//    @GetMapping(value = "", produces = "application/hal+json")
//    public CollectionModel<EntityModel<DefenderDTO>> getAllDefenders {
//        List<Defender> defenderList = defenderService.findall();
//        List<DefenderDTO> defenderDTOList = modelMapper.map(defenderList, List<DefenderDTO.class>.class);
//    }

}
