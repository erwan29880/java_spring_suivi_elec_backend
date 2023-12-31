package fr.erwan.elec.security;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * vérification lors du signIn
 */
public class UsernamePasswordAuthFilter extends OncePerRequestFilter {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final UserAuthenticationProvider userAuthenticationProvider;

    private final String login = "pseudo";

    public UsernamePasswordAuthFilter(UserAuthenticationProvider userAuthenticationProvider) {
        this.userAuthenticationProvider = userAuthenticationProvider;
    }

    /**
     * vérification lors du signIn
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain filterChain) throws ServletException, IOException {

        if ("/elec/signIn".equals(req.getServletPath()) && HttpMethod.POST.matches(req.getMethod())) {
            CredentialsDto credentialsDto = new CredentialsDto();

            // vérifier le parsage du json
            try {
                credentialsDto = MAPPER.readValue(req.getInputStream(), CredentialsDto.class);
                if (credentialsDto.getLogin().equals(this.login) == false) {
                    System.out.println("passé par la");
                    char[] fake = {'a'};
                    credentialsDto.setPassword(fake);
                }
            } catch (Exception e) {
                throw e;
            }

            try {
                SecurityContextHolder
                    .getContext()
                    .setAuthentication(userAuthenticationProvider.validateCredentials(credentialsDto));
                } catch (RuntimeException e) {
                    SecurityContextHolder
                    .clearContext();
                    throw e;
                }
            }
        filterChain.doFilter(req, res);
    }
}
