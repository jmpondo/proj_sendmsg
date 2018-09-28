package com.sendmsg.entitie.message;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sendmsg.entities.authentification.Utilisateur;
import com.sendmsg.entities.recipient.Campagne;
import com.sendmsg.entities.recipient.Recipiendaire;

@Entity
@Table(name="notification")
public class Notification implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(length=2)
	private int statut;
	private Date dateNotification;
	@Column(name="smsEnvoyé")
	private int smsCount;
	@ManyToOne
	@JoinColumn(name="campagneId", nullable=true)
	private Campagne campagne;
	@ManyToOne
	@JoinColumn(name="recipiendaireId", nullable=true)
	private Recipiendaire recipiendaire;
	@ManyToOne
	@JoinColumn(name="gatewayId", nullable=true)
	private GatewayParam gateway;	
	@ManyToOne
	@JoinColumn(name="userId", nullable=true)
	private Utilisateur utilisateur;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getStatut() {
		return statut;
	}
	public void setStatut(int statut) {
		this.statut = statut;
	}
	public Date getDateNotification() {
		return dateNotification;
	}
	public void setDateNotification(Date dateNotification) {
		this.dateNotification = dateNotification;
	}
	
	public int getSmsCount() {
		return smsCount;
	}
	public void setSmsCount(int smsCount) {
		this.smsCount = smsCount;
	}
	public Campagne getCampagne() {
		return campagne;
	}
	public void setCampagne(Campagne campagne) {
		this.campagne = campagne;
	}
	public Recipiendaire getRecipiendaire() {
		return recipiendaire;
	}
	public void setRecipiendaire(Recipiendaire recipiendaire) {
		this.recipiendaire = recipiendaire;
	}
	public GatewayParam getGateway() {
		return gateway;
	}
	public void setGateway(GatewayParam gateway) {
		this.gateway = gateway;
	}
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	public Notification(Long id, int statut, Date dateNotification, int smsCount, Campagne campagne,
			Recipiendaire recipiendaire, GatewayParam gateway, Utilisateur utilisateur) {
		super();
		this.id = id;
		this.statut = statut;
		this.dateNotification = dateNotification;
		this.smsCount = smsCount;
		this.campagne = campagne;
		this.recipiendaire = recipiendaire;
		this.gateway = gateway;
		this.utilisateur = utilisateur;
	}
	public Notification() {
		super();
		// TODO Auto-generated constructor stub
	}	

}
