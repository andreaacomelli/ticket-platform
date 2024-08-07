package it.comelli.ticket.security;

import it.comelli.ticket.model.Utente;
import it.comelli.ticket.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Utente> optionalUtente = utenteRepository.findByUsername(username);
        
      
        Utente utente = optionalUtente.orElseThrow(() -> new UsernameNotFoundException("Utente non trovato: " + username));
        
        return new org.springframework.security.core.userdetails.User(
            utente.getUsername(),
            utente.getPassword(),
            getAuthorities(utente)
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Utente utente) {
        return utente.getRuoli().stream()
            .map(ruolo -> new SimpleGrantedAuthority(ruolo.getNome()))
            .collect(Collectors.toList());
    }
}