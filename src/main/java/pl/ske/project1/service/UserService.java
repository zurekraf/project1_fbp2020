package pl.ske.project1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ske.project1.entity.ApplicationUser;
import pl.ske.project1.repository.ApplicationUserRepository;

import java.util.Optional;

@Service
public class UserService implements IUserService{

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    public ApplicationUser getByUsername(String username) {
        return applicationUserRepository.findByUsername(username);
    }

    public Optional<ApplicationUser> getById(Long id) {
        return applicationUserRepository.findById(id);
    }

}
