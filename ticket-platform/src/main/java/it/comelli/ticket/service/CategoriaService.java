package it.comelli.ticket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.comelli.ticket.model.Categoria;
import it.comelli.ticket.repository.CategoriaRepository;


@Service
public class CategoriaService {
	
	@Autowired
    private CategoriaRepository categoriaRepository;

	public List<Categoria> getCategorie() {
        return categoriaRepository.findAll();
    }

}
