package it.comelli.ticket.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.comelli.ticket.model.Utente;
import it.comelli.ticket.service.UtenteService;

@RestController
@CrossOrigin
@RequestMapping("/api/operatori")
public class UtentiController {

    @Autowired
    private UtenteService utenteService;
    
    @GetMapping
    public List<Utente> getUtentiDisponibili() {
        return utenteService.getUtenteDisponibile();
    }

    @GetMapping("/{id}")
    public Utente getOperatoreById(@PathVariable Integer id) {
        return utenteService.getUtenteById(id);
    }

    @PutMapping("/{id}")
    public Utente updateUtente(@PathVariable Integer id, @RequestBody Utente utente) {
        utente.setId(id);
        return utenteService.updateUtente(utente);
    }
}