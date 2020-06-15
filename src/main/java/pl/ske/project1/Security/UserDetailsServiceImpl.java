package pl.ske.project1.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.ske.project1.User.MyUser;
import pl.ske.project1.entity.ApplicationUser;
import pl.ske.project1.repository.ApplicationUserRepository;
import pl.ske.project1.repository.CartRepository;

import java.util.HashSet;
import java.util.Set;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private ApplicationUserRepository applicationUserRepository;

    public UserDetailsServiceImpl(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("USER_DETAIL_SERVICE");
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(username);
        }
        /*
        loadUserByUsername wykonuje się TYLKO przy logowaniu, więc jeśli
        nie pakujemy roles do klucza, to chyba nie ma po co tutaj pobierać jego roli -> (funkcja getAuthority)

        System.out.println(getAuthority(applicationUser));
        return new User(applicationUser.getUsername(), applicationUser.getPassword(), getAuthority(applicationUser));
         */

//        return new User(applicationUser.getUsername(), applicationUser.getPassword(), emptyList());
        return builtCustomUser(applicationUser);
    }
    /*
    private Set getAuthority(ApplicationUser user) {
        Set authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }
     */
    private User builtCustomUser(ApplicationUser applicationUser) {
        String username = applicationUser.getUsername();
        String password = applicationUser.getPassword();

        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        //public MyUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired,
        //                  boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities)

        MyUser myUser = new MyUser(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, emptyList());
//        myUser.setTest("alamakota");

        return myUser;
    }
}
