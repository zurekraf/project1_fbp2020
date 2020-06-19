package pl.ske.project1.Security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Service;
import pl.ske.project1.entity.ApplicationUser;
import pl.ske.project1.entity.Role;
import pl.ske.project1.repository.ApplicationUserRepository;
import pl.ske.project1.service.IUserService;
import pl.ske.project1.service.ProductService;
import pl.ske.project1.service.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.web.util.WebUtils;

import static pl.ske.project1.Security.SecurityConstants.HEADER_STRING;
import static pl.ske.project1.Security.SecurityConstants.SECRET;
import static pl.ske.project1.Security.SecurityConstants.TOKEN_PREFIX;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final IUserService userService;

    public JWTAuthorizationFilter(AuthenticationManager authManager, IUserService userService) {
        super(authManager);
        this.userService = userService;
    }



    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        System.out.println("AUTHORIZATION_FILTER_DO_FILTER_INTERNAL");
        String header = null;
        header = req.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);

        if (token != null) {
            // parse the token.
            String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();

            /*
            //może być też .getClaims jeśli są w tokenie
            String roles = ("ADMIN");
            List<GrantedAuthority> grantedAuths = AuthorityUtils.commaSeparatedStringToAuthorityList(roles);
             */

            if (user != null) {
                ApplicationUser applicationUser = userService.getByUsername(user);
                List<GrantedAuthority> grantedAuths = new ArrayList<>();

                if(applicationUser.getRoles() != null) {
                    Set<Role> roles = applicationUser.getRoles();
                    roles.forEach((role) -> {
                        grantedAuths.add(new SimpleGrantedAuthority(role.getName()));
                    });
                }
//                //_____
//                UserDetails userDetails = new UserDetails()
                ApplicationUser usr = userService.getByUsername(user);
                UsernamePasswordAuthenticationToken u = new UsernamePasswordAuthenticationToken(usr, null, grantedAuths);
//                u.setDetails();
//                //_____

                //loooooooooooooooooooool credential to jest wczesniej password

                return new UsernamePasswordAuthenticationToken(user, null, grantedAuths);
                //return new UsernamePasswordAuthenticationToken(usr, null, grantedAuths);
            }
            return null;
        }
        return null;
    }

}
