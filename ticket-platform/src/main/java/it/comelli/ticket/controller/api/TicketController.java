package it.comelli.ticket.controller.api;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.comelli.ticket.model.Nota;
import it.comelli.ticket.model.Ticket;
import it.comelli.ticket.service.TicketService;

@RestController
@CrossOrigin
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketService.getTickets();
    }

    @GetMapping("/{id}")
    public Ticket getTicketById(@PathVariable Integer id) {
        return ticketService.getTicketById(id);
    }

    @PostMapping
    public Ticket createTicket(@RequestBody Ticket ticket) {
        return ticketService.createTicket(ticket);
    }

    @PutMapping("/{id}")
    public Ticket updateTicket(@PathVariable Integer id, @RequestBody Ticket ticket) {
        ticket.setId(id);
        return ticketService.updateTicketStatus(id, "");
    }

    @DeleteMapping("/{id}")
    public void deleteTicket(@PathVariable Integer id) {
        ticketService.deleteTicket(id);
    }
    
    @GetMapping("/data")
    public List<Ticket> getTicketsByData(@PathVariable LocalDateTime dataCreazione){
    	return ticketService.geTicketsByData(dataCreazione);
    }

    @GetMapping("/categoria/{categoriaId}")
    public List<Ticket> getTicketsByCategoria(@PathVariable Integer categoriaId) {
        return ticketService.getTicketsByCategoria(categoriaId);
    }

    @GetMapping("/stato/{stato}")
    public List<Ticket> getTicketsByStato(@PathVariable String stato) {
        return ticketService.getTicketsByStato(stato);
    }

    @PostMapping("/{id}/note")
    public void addNota(@PathVariable Integer id, @RequestBody Nota nota) {
        ticketService.addNota(id, nota);
    }
}