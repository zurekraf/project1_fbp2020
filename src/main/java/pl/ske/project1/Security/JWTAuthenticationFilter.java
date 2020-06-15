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
import pl.ske.project1.User.MyUser;
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

        //WYKONUJE SIĘ TYLKO PRZY LOGOWANIU

        /*
        String roles = ("ADMIN");
        List<GrantedAuthority> grantedAuths = AuthorityUtils.commaSeparatedStringToAuthorityList(roles);
         */

        try {
            ApplicationUser creds = null;
            if(req.getParameter("username") != null && req.getParameter("password") != null) {
                //username+password jako parametry -> post z form
                creds = new ApplicationUser();
                creds.setUsername(req.getParameter("username"));
                creds.setPassword(req.getParameter("password"));
            } else {
                //username+password jako json
                creds = new ObjectMapper().readValue(req.getInputStream(), ApplicationUser.class);
            }
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        System.out.println("unsuccesfullAuthentication");
        //to można wywalić bo to standardowe override i nic tu nie zmieniam
        super.unsuccessfulAuthentication(request, response, failed);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
        /*
        token zwracany w odpowiedzi na poprawny /login
        i tutaj do response dodawane jest cookie

        tutaj też dodaje się role do tokenu:
        .withClaim("roles", "ADMIN, USER, TEST")
         */

        //_________
        //OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOoo
        ((MyUser) auth.getPrincipal()).getTest();
        //_________

        String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);

        Cookie cookie1 = new Cookie("Authorization", TOKEN_PREFIX+token);
        cookie1.setMaxAge(24 * 60 * 60);
        res.addCookie(cookie1);
    }
}
