package it.comelli.ticket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.comelli.ticket.model.Nota;

public interface NotaRepository extends JpaRepository<Nota, Integer> {
    List<Nota> findByTicketId(Integer ticketId);
}