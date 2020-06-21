package pl.ske.project1.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ske.project1.HATEOAS.ChargeModelAssembler;
import pl.ske.project1.entity.Charge;
import pl.ske.project1.service.ChargeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/charges")
public class ChargeController {
    @Autowired
    private ChargeService chargeService;
    @Autowired
    private ChargeModelAssembler chargeModelAssembler;

    @GetMapping(value = "", produces = "application/hal+json")
    public CollectionModel<EntityModel<Charge>> getAllCharges() {
        List<Charge> chargesList = chargeService.findall();
        return chargeModelAssembler.toCollectionModel(chargesList);
    }

    @GetMapping(value = "/{id}", produces = "application/hal+json")
    public EntityModel<Charge> getChargeById(@PathVariable Long id) {
        Optional<Charge> charge = chargeService.findById(id);
        return chargeModelAssembler.toModel(charge.get());
    }
}
