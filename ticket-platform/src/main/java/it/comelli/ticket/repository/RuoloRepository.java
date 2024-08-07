package it.comelli.ticket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.comelli.ticket.model.Ruolo;

public interface RuoloRepository extends JpaRepository<Ruolo, Integer> {
	
	List<Ruolo> findByNome(String nome);
}
