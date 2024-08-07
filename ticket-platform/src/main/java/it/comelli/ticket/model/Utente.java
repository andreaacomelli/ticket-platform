package it.comelli.ticket.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class Utente {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Email", nullable = false, unique = true)
    private String email;
    
    @Column(name = "Username", nullable = false, unique = true)
    private String username;
    
	@Column(name = "Password", nullable = false)
    private String password;
    
	@Column(name = "Disponibilità", nullable = false)
    private boolean disponibile;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Ruolo> ruoli;
	
	 @OneToMany(mappedBy = "operatore")
	 private List<Ticket> tickets;
    
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public boolean isDisponibile() {
		return disponibile;
	}
	public void setDisponibile(boolean disponibile) {
		this.disponibile = disponibile;
	}
	
	public List<Ruolo> getRuoli() {
		return ruoli;
	}
	public void setRuoli(List<Ruolo> ruoli) {
		this.ruoli = ruoli;
	}
	public List<Ticket> getTickets() {
		return tickets;
	}
}
