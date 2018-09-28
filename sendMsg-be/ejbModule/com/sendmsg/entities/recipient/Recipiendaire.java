package com.sendmsg.entities.recipient;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="recipiendaire")
public class Recipiendaire implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;	
	@Column(unique=true, length=17)
	private String telephone;
	@Column(length=30)
    private String nom;
	@Column(length=50)
    private String prenom;
	@Column(length=30)
    private String commune;
	@Column(length=17)
    private String aspiration;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getCommune() {
		return commune;
	}
	public void setCommune(String commune) {
		this.commune = commune;
	}
	public String getAspiration() {
		return aspiration;
	}
	public void setAspiration(String aspiration) {
		this.aspiration = aspiration;
	}
	public Recipiendaire(Long id, String telephone, String nom, String prenom, String commune, String aspiration) {
		super();
		this.id = id;
		this.telephone = telephone;
		this.nom = nom;
		this.prenom = prenom;
		this.commune = commune;
		this.aspiration = aspiration;
	}
	public Recipiendaire() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
