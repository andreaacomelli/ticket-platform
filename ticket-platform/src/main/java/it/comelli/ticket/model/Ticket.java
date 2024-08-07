package it.comelli.ticket.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
public class Ticket {
   
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	@Column(name = "Titolo", nullable = false)
    private String titolo;
	
	@Column(name = "Descrizione")
    private String descrizione;
	
    @Column(name = "Stato", nullable = false)
    private String stato;

    @ManyToOne
    @JoinColumn(name = "operatore_id")
    private Utente operatore;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @Column(name = "Data_Creazione", nullable = false)
    private LocalDateTime data;
    
    @Column(name = "Data_Modifica")
    private LocalDateTime dataModifica;
   
    public Utente getUtente() {
		return operatore;
	}
	public void setUtente(Utente operatore) {
		this.operatore = operatore;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	public LocalDateTime getDataCreazione() {
		return data;
	}
	public void setDataCreazione(LocalDateTime data) {
		this.data = data;
	}
	public LocalDateTime getDataModifica() {
		return dataModifica;
	}
	public void setDataModifica(LocalDateTime dataModifica) {
		this.dataModifica = dataModifica;
	}
}
