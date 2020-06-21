package pl.ske.project1.accessRestriction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pl.ske.project1.entity.ApplicationUser;
import pl.ske.project1.repository.ApplicationUserRepository;
import pl.ske.project1.service.UserService;

import java.io.Serializable;

public class CustomPermissionEvaluator implements PermissionEvaluator {

    private UserService userService;

    public CustomPermissionEvaluator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean hasPermission(Authentication auth, Object targetDomainObject, Object permission) {

        System.out.println("custom permission evaluator");
        //System.out.println(auth.getPrincipal());

        SimpleGrantedAuthority roleAdmin = new SimpleGrantedAuthority("ADMIN");
//        if(auth.getAuthorities().contains(roleAdmin)) {
//            System.out.println("TO admin");
//        } else {
//            System.out.println("to nie jest admin");
//        }

        //___________
        //System.out.println(auth.getName());
        //Long userId = userService.getByUsername(auth.getName()).getId();
        //___________

        ApplicationUser applicationUser = (ApplicationUser) auth.getPrincipal();

        Long targetId = (Long) targetDomainObject;
        String permissionStr = (String) permission;

//        if(permissionStr.equals("getOrder")) {
//            if(auth.getAuthorities().contains(roleAdmin) || userId.equals(targetId)) {
//                System.out.println("_____dostep ok_____");
//                return true;
//            } else {
//                System.out.println("____brak_dostepu_____");
//                return false;
//            }
//        }
        //tu można też sprawdzać inne permissionStr
        //_____
        if(permissionStr.equals("updateDefender")) {
            Long userDefenderId = null;
            if(applicationUser.getDefender() != null) {
                userDefenderId = applicationUser.getDefender().getId();
            } else {
                return false;
            }
//            Long userDefenderId = applicationUser.getDefender().getId();

            System.out.println("userdefenderId: " + userDefenderId);
            System.out.println("targetId: " + targetId);

            if(userDefenderId.equals(targetId)) {
                System.out.println("_____ ok_____");
                return true;
            } else {
                System.out.println("_____not ok ______");
                return false;
            }
        }
        //_____
        return false;

    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }
}
