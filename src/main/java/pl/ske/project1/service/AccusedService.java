package pl.ske.project1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.ske.project1.entity.Accused;
import pl.ske.project1.repository.AccusedRepository;

import java.util.List;
import java.util.Optional;

@Service
@Component
public class AccusedService {
    @Autowired
    private AccusedRepository accusedRepository;

    public List<Accused> findall() {
        return (List<Accused>) accusedRepository.findAll();
    }

    public Optional<Accused> findById(Long accusedId) {
        return accusedRepository.findById(accusedId);
    }

}
