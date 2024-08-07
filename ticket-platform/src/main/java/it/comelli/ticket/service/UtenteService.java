package it.comelli.ticket.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.comelli.ticket.model.Utente;
import it.comelli.ticket.repository.TicketRepository;
import it.comelli.ticket.repository.UtenteRepository;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;
    
    @Autowired
    private TicketRepository ticketRepository;

    public List<Utente> getUtenteDisponibile() {
        return utenteRepository.findByDisponibile(true);
    }

    public Utente updateUtente(Utente utente) {
        return utenteRepository.save(utente);
    }
    public Utente getUtenteById(Integer id) {
        Optional<Utente> optionalUtente = utenteRepository.findById(id);
        return optionalUtente.orElseThrow(() -> new RuntimeException("Utente non trovato con id: " + id));
    }
    public List<Utente> findAllUtenti() {
        return utenteRepository.findAll();
    }

	public Optional<Utente> getUtenteByUsername(String username) {
		return utenteRepository.findByUsername(username);
	}
	
	public boolean noTicketAssegnati(Integer operatoreId) {
        return ticketRepository.countByOperatoreId(operatoreId) == 0;
    }
	
	public void updateStatoOperatore(Integer id, Boolean stato) {
        Utente operatore = getUtenteById(id);
        if (operatore != null && noTicketAssegnati(id)) {
            operatore.setDisponibile(stato);
            utenteRepository.save(operatore);
        }
    }

	public void save(Utente operatore) {
	    utenteRepository.save(operatore);
	}

}
