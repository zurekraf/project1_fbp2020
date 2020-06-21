package pl.ske.project1.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.ske.project1.entity.ApplicationUser;
import pl.ske.project1.repository.ApplicationUserRepository;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private ApplicationUserRepository applicationUserRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("")
    public List<ApplicationUser> getUsers() {
        List<ApplicationUser> userList = applicationUserRepository.findAll();
        return userList;
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody ApplicationUser user) {

        ApplicationUser u = null;
        u = applicationUserRepository.findByUsername(user.getUsername());
        if(u == null) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            applicationUserRepository.save(user);
        }
    }
}
