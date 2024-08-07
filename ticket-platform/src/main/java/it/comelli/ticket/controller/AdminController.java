package it.comelli.ticket.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.comelli.ticket.model.Nota;
import it.comelli.ticket.model.Ticket;
import it.comelli.ticket.model.Utente;
import it.comelli.ticket.service.CategoriaService;
import it.comelli.ticket.service.NotaService;
import it.comelli.ticket.service.TicketService;
import it.comelli.ticket.service.UtenteService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private TicketService ticketService;
    
    @Autowired
    private UtenteService utenteService;
    
    @Autowired
    private NotaService notaService;
    
    @Autowired
    private CategoriaService categoriaService;
    
    // Visualizza operatori
    @GetMapping("/operatori")
    public String listaOperatori(Model model) {
        List<Utente> operatori = utenteService.getUtenteDisponibile();
        model.addAttribute("operatori", operatori);
        return "admin/operatori";
    }
    
    // Visualizzazione tabella ticket e ricerca
    @GetMapping("/dashboard")
    public String dashboard(Model model, @RequestParam(required = false) String searchText) {
        List<Ticket> tickets;
        if (searchText != null && !searchText.isEmpty()) {
            tickets = ticketService.getTicketsByTitolo(searchText);
        } else {
            tickets = ticketService.getTickets();
        }
        model.addAttribute("tickets", tickets);
        return "admin/dashboard";
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

    // Aggiunta nota al ticket
    @PostMapping("/ticket/{id}/nota")
    public String addNota(@PathVariable Integer id, @RequestParam String text) {
        Nota nuovaNota = new Nota();
        nuovaNota.setTesto(text);
        nuovaNota.setDataCreazione(LocalDateTime.now());
        nuovaNota.setAutore("Admin");

        notaService.addNotaToTicket(id, nuovaNota);

        return "redirect:/admin/ticket/" + id;
    }
    
    // Visualizza form per creare nuovo ticket
    @GetMapping("/ticket/new")
    public String showCreateTicketForm(Model model) {
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("categorie", categoriaService.getCategorie());
        model.addAttribute("operatori", utenteService.getUtenteDisponibile());
        return "tickets/create-ticket";
    }

    // Crea nuovo ticket
    @PostMapping("/ticket/new")
    public String createTicket(@ModelAttribute Ticket ticket) {
        ticketService.createTicket(ticket);
        return "redirect:/admin/dashboard";
    }

    // Visualizza form per modificare un ticket esistente
    @GetMapping("/ticket/{id}/edit")
    public String showEditTicketForm(@PathVariable Integer id, Model model) {
        Ticket ticket = ticketService.getTicketById(id);
        model.addAttribute("ticket", ticket);
        model.addAttribute("categorie", categoriaService.getCategorie());
        return "tickets/edit-ticket";
    }

    // Aggiorna un ticket esistente
    @PostMapping("/ticket/{id}/edit")
    public String updateTicket(@PathVariable Integer id, @ModelAttribute Ticket ticket) {
        ticketService.updateTicket(id, ticket);
        return "redirect:/admin/ticket/" + id;
    }
}
