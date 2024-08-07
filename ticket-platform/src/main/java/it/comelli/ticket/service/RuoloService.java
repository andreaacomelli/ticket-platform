package it.comelli.ticket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.comelli.ticket.model.Ruolo;
import it.comelli.ticket.repository.RuoloRepository;

@Service
public class RuoloService {
	
	@Autowired
    private RuoloRepository categoriaRepository;
	
	
	public List<Ruolo> findOperatori() {
        return categoriaRepository.findByNome("OPERATORE");
    }
}
