package pl.ske.project1.Security;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.ske.project1.entity.ApplicationUser;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import static pl.ske.project1.Security.SecurityConstants.EXPIRATION_TIME;
import static pl.ske.project1.Security.SecurityConstants.HEADER_STRING;
import static pl.ske.project1.Security.SecurityConstants.SECRET;
import static pl.ske.project1.Security.SecurityConstants.TOKEN_PREFIX;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {

        //
//        String roles = ("ADMIN");
//        List<GrantedAuthority> grantedAuths = AuthorityUtils.commaSeparatedStringToAuthorityList(roles);
        //

        System.out.println("!!!!!!!!!");

        try {
            ApplicationUser creds = null;
            if(req.getParameter("username") != null && req.getParameter("password") != null) {
                //____________
                System.out.println("z parametrami");
                //____________
                creds = new ApplicationUser();
                creds.setUsername(req.getParameter("username"));
                creds.setPassword(req.getParameter("password"));
            } else {
                //______________
                System.out.println("jsoon");
                //____________
                creds = new ObjectMapper().readValue(req.getInputStream(), ApplicationUser.class);
            }
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {

        //___________________________________________________
        System.out.println("POPRAWNA AUTENTYKACJA");
        //___________________________________________________

        String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) //.withClaim("roles", "ADMIN, USER")
                .sign(HMAC512(SECRET.getBytes()));
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        //to jest dodawane do odpowiedzi na /login
        //res.addHeader("lol", "alamakota");
        Cookie cookie1 = new Cookie("access_token", token);
        res.addCookie(cookie1);
    }

}
