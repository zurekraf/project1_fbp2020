package pl.ske.project1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.ske.project1.entity.Charge;
import pl.ske.project1.repository.ChargeRepository;

import java.util.List;
import java.util.Optional;

@Service
@Component
public class ChargeService {
    @Autowired
    private ChargeRepository chargeRepository;

    public List<Charge> findall() {
        return (List<Charge>) chargeRepository.findAll();
    }

    public Optional<Charge> findById(Long chargeId) {
        return chargeRepository.findById(chargeId);
    }

    public Charge createCharge(Charge newCharge) {
        return chargeRepository.save(newCharge);
    }
}
