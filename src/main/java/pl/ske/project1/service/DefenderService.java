package pl.ske.project1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.ske.project1.entity.Defender;
import pl.ske.project1.repository.DefenderRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Component
public class DefenderService {
    @Autowired
    private DefenderRepository defenderRepository;

    public List<Defender> findByLastName(String lastName) {
        return defenderRepository.findByLastName(lastName);
    }

    public List<Defender> findall() {
        return (List<Defender>) defenderRepository.findAll();
    }

    public Optional<Defender> findById(Long defenderId) {
        return defenderRepository.findById(defenderId);
    }

    public Defender createDefender(Defender newDefender) {
        return defenderRepository.save(newDefender);
    }

    public Optional<Defender> updateDefender(Map<String, Object> updates, Long defenderId) {
        Optional<Defender> defenderById = defenderRepository.findById(defenderId);
        if(defenderById.isPresent()) {
            Defender defender = defenderById.get();
            if(updates.containsKey("firstName")) {
                defender.setFirstName((String) updates.get("firstName"));
            }
            if(updates.containsKey("lastName")) {
                defender.setLastName((String) updates.get("lastName"));
            }
            if(updates.containsKey("officeAddress")) {
                defender.setOfficeAddress((String) updates.get("officeAddress"));
            }
            if(updates.containsKey("phoneNumber")) {
                defender.setPhoneNumber((String) updates.get("phoneNumber"));
            }
            defenderRepository.save(defender);
        }
        return defenderById;
    }
}
