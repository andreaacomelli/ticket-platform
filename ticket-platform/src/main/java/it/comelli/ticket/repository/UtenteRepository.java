package it.comelli.ticket.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.comelli.ticket.model.Utente;

public interface UtenteRepository extends JpaRepository<Utente, Integer> {
	
	Optional <Utente> findByUsername(String username);
	Optional <Utente> findById(Integer id);
	List<Utente> findByDisponibile(Boolean disponibile);
}