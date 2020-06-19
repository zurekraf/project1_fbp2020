package pl.ske.project1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.ske.project1.entity.CourtCase;
import pl.ske.project1.entity.Defender;
import pl.ske.project1.entity.Product;
import pl.ske.project1.repository.DefenderRepository;
import pl.ske.project1.repository.ProductRepository;

import java.util.List;
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
}
