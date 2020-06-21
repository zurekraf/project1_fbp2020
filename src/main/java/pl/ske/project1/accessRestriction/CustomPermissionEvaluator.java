package pl.ske.project1.accessRestriction;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pl.ske.project1.entity.ApplicationUser;
import pl.ske.project1.service.UserService;

import java.io.Serializable;

public class CustomPermissionEvaluator implements PermissionEvaluator {

    private UserService userService;

    public CustomPermissionEvaluator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean hasPermission(Authentication auth, Object targetDomainObject, Object permission) {

        /*
        SimpleGrantedAuthority roleAdmin = new SimpleGrantedAuthority("ADMIN");
        if(auth.getAuthorities().contains(roleAdmin)) {

        } else {

        }
         */

        ApplicationUser applicationUser = (ApplicationUser) auth.getPrincipal();

        Long targetId = (Long) targetDomainObject;
        String permissionStr = (String) permission;

        if(permissionStr.equals("updateDefender")) {
            Long userDefenderId = null;
            if(applicationUser.getDefender() != null) {
                userDefenderId = applicationUser.getDefender().getId();
            } else {
                return false;
            }

            if(userDefenderId.equals(targetId)) {
                return true;
            } else {
                return false;
            }
        }
        //kolejny if może sprawdzać inny permissionStr
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }
}
