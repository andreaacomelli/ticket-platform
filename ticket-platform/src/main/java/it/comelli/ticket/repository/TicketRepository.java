package it.comelli.ticket.repository;

import java.time.LocalDateTime;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import it.comelli.ticket.model.Ticket;
import it.comelli.ticket.model.Utente;
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
	
	List<Ticket> findByCategoriaId(Integer categoriaId);
    List<Ticket> findByStato(String stato);
	List<Ticket> findByData(LocalDateTime dataCreazione);
	List<Ticket> findByTitolo(String title);
	List<Ticket> findByOperatore(Utente operatore);
	int countByOperatoreId(Integer operatoreId);
}
