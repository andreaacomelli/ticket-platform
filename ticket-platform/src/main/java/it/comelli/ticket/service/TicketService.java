package it.comelli.ticket.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import it.comelli.ticket.model.Nota;
import it.comelli.ticket.model.Ticket;
import it.comelli.ticket.model.Utente;
import it.comelli.ticket.repository.NotaRepository;
import it.comelli.ticket.repository.TicketRepository;
import it.comelli.ticket.repository.UtenteRepository;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UtenteRepository operatoreRepository;
    
    @Autowired
    private NotaRepository notaRepository;

    public Ticket createTicket(Ticket ticket) {
        if (!operatoreRepository.findById(ticket.getUtente().getId()).get().isDisponibile()) {
            throw new IllegalArgumentException("Operatore non disponibile");
        }
        ticket.setDataCreazione(LocalDateTime.now());
        return ticketRepository.save(ticket);
    }

    public Ticket updateTicket(Integer id, Ticket updatedTicket) {
        Ticket existingTicket = getTicketById(id);
        existingTicket.setTitolo(updatedTicket.getTitolo());
        existingTicket.setDescrizione(updatedTicket.getDescrizione());
        existingTicket.setStato(updatedTicket.getStato());
        existingTicket.setCategoria(updatedTicket.getCategoria());
        existingTicket.setDataModifica(LocalDateTime.now());
        return ticketRepository.save(existingTicket);
    }

    public List<Ticket> getTickets() {
        return ticketRepository.findAll();
    }

    public List<Ticket> getTicketsByCategoria(Integer categoriaId) {
        return ticketRepository.findByCategoriaId(categoriaId);
    }

    public List<Ticket> getTicketsByStato(String stato) {
        return ticketRepository.findByStato(stato);
    }
    
    public List<Ticket> geTicketsByData(LocalDateTime dataCreazione){
    	return ticketRepository.findByData(dataCreazione);
    }

    public Ticket getTicketById(Integer id) {
        return ticketRepository.findById(id).orElseThrow();
    }

    public void deleteTicket(Integer id) {
        ticketRepository.deleteById(id);
    }

    public void addNota(Integer ticketId, Nota nota) {
        Ticket ticket = getTicketById(ticketId);
        nota.setTicket(ticket);
        nota.setDataCreazione(LocalDateTime.now());
        notaRepository.save(nota);
    }

    public List<Ticket> getTicketsByTitolo(String title) {
        return ticketRepository.findByTitolo(title);
    }

    public List<Ticket> getTicketsbyOperatore(Utente operatoreParam) {
        String username = getCurrentUsername();
        operatoreRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Operatore non trovato: " + username));
       
        return ticketRepository.findByOperatore(operatoreParam);
    }
	
	private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

	public Ticket updateTicketStatus(Integer id, String stato) {
        Ticket ticket = getTicketById(id);
        if (ticket != null) {
            ticket.setStato(stato);
            ticketRepository.save(ticket);
        }
		return ticketRepository.save(ticket);
    }
}