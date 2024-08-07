package it.comelli.ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.comelli.ticket.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{

}
