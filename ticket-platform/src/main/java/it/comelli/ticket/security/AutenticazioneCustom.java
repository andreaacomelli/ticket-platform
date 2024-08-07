package it.comelli.ticket.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AutenticazioneCustom implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String role = authentication.getAuthorities().stream()
            .map(authority -> authority.getAuthority())
            .filter(authority -> authority.equals("ADMIN") || authority.equals("OPERATORE"))
            .findFirst()
            .orElse("");

        if (role.equals("ADMIN")) {
            response.sendRedirect("/admin/dashboard");
        } else if (role.equals("OPERATORE")) {
            response.sendRedirect("/operatore/dashboard");
        } else {
            response.sendRedirect("/");
        }
    }
}
