package it.comelli.ticket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.comelli.ticket.model.Nota;
import it.comelli.ticket.model.Ticket;
import it.comelli.ticket.repository.NotaRepository;
import it.comelli.ticket.repository.TicketRepository;
import jakarta.transaction.Transactional;

@Service
public class NotaService {
    @Autowired
    private NotaRepository notaRepository;
    
    @Autowired
    private TicketRepository ticketRepository;

    public List<Nota> getNoteByTicketId(Integer ticketId) {
        return notaRepository.findByTicketId(ticketId);
    }
    
    @Transactional
    public void addNotaToTicket(Integer ticketId, Nota nota) {
        Ticket ticket = ticketRepository.findById(ticketId)
            .orElseThrow(() -> new RuntimeException("Ticket non trovato"));
        nota.setTicket(ticket);
        notaRepository.save(nota);
    }
}