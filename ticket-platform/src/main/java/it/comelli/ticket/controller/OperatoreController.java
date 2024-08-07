package it.comelli.ticket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.comelli.ticket.model.Nota;
import it.comelli.ticket.model.Ticket;
import it.comelli.ticket.model.Utente;
import it.comelli.ticket.service.NotaService;
import it.comelli.ticket.service.TicketService;
import it.comelli.ticket.service.UtenteService;

@Controller
@RequestMapping("/operatore")
public class OperatoreController {

    @Autowired
    private TicketService ticketService;
    
    @Autowired
    private UtenteService utenteService;
    
    @Autowired
    private NotaService notaService;
    
    // Visualizzazione dashboard con logica per settare lo stato 
    @GetMapping("/dashboard")
    public String operatoreDashboard(Model model, Authentication authentication, Integer id) {
        String username = authentication.getName();
        Utente operatore = utenteService.getUtenteByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        operatore.getId();
        List<Ticket> tickets = ticketService.getTicketsbyOperatore(operatore);
        
        boolean hasTickets = !tickets.isEmpty();
        
        if (hasTickets) {
            operatore.setDisponibile(false);
            utenteService.save(operatore);
        }
        
        model.addAttribute("operatore", operatore);
        model.addAttribute("tasks", tickets);
        model.addAttribute("canUpdateStato", ticketService.getTicketsbyOperatore(operatore).isEmpty());
        return "operatore/dashboard";
    }

    
    // Visualizzazione dettagli ticket
    @GetMapping("/ticket/{id}")
    public String viewTicket(@PathVariable Integer id, Model model) {
        Ticket ticket = ticketService.getTicketById(id);
        List<Nota> note = notaService.getNoteByTicketId(id);
        model.addAttribute("ticket", ticket);
        model.addAttribute("note", note);
        return "tickets/ticket-detail";
    }
    
    // Modifica stato lavorazione ticket
    @PostMapping("/ticket/{id}/updateStatus")
    public String updateStatus(@PathVariable Integer id, @RequestParam String stato) {
        ticketService.updateTicketStatus(id, stato);
        return "redirect:/operatore/ticket/" + id;
    }
    
    // Modifica stato disponibilità
    @PostMapping("/updateStatoUtente")
    public String updateStato(@RequestParam Integer id, @RequestParam boolean stato, Model model) {
        if (utenteService.noTicketAssegnati(id)) {
            utenteService.updateStatoOperatore(id, stato);
        } else {
            model.addAttribute("updateError", "Non puoi aggiornare il tuo stato se hai ticket assegnati.");
        }
        return "redirect:/operatore/dashboard";
    }
    
    //Aggiunta note
    @PostMapping("/{id}/ticket/{ticketId}/note")
    public String addNote(@PathVariable Integer id, @PathVariable Integer ticketId, @RequestParam String text) {
        Nota nota = new Nota();
        nota.setAutore("Operatore");
        nota.setTesto(text);
        ticketService.addNota(ticketId, nota);
        return "redirect:/operatore/" + id + "/ticket/" + ticketId;
    }
}

