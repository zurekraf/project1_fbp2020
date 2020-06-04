package pl.ske.project1.Security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static pl.ske.project1.Security.SecurityConstants.HEADER_STRING;

public class SecondCustomFilter extends BasicAuthenticationFilter {
    public SecondCustomFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest(request);
        String header = null;
        header = request.getHeader(HEADER_STRING);
        if (header == null) {
            Cookie c = WebUtils.getCookie(request, "Authorization");
            if(c != null) {
                header = c.getValue();
                mutableRequest.putHeader(c.getName(), c.getValue());
            }
        }
        chain.doFilter(mutableRequest, response);
    }
}
