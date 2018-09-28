package com.sendmsg.entities.recipient;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sendmsg.entitie.message.Message;



@Entity
@Table(name="campagne")
public class Campagne implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(unique=true, length=20)
	private String codeCampagne;
	@Column(length=30)
	private String nomCampagne;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="messageId")
	private Message message;
	
	
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCodeCampagne() {
		return codeCampagne;
	}
	public void setCodeCampagne(String codeCampagne) {
		this.codeCampagne = codeCampagne;
	}
	public String getNomCampagne() {
		return nomCampagne;
	}
	public void setNomCampagne(String nomCampagne) {
		this.nomCampagne = nomCampagne;
	}
	public Campagne(Long id, String nomCampagne, Message message) {
		super();
		this.id = id;
		this.nomCampagne = nomCampagne;
		this.message = message;
	}
	public Campagne() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "Campagne [id=" + id + ", codeCampagne=" + codeCampagne + ", nomCampagne=" + nomCampagne + ", message="
				+ message + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Campagne other = (Campagne) obj;
		if (codeCampagne == null) {
			if (other.codeCampagne != null)
				return false;
		} else if (!codeCampagne.equals(other.codeCampagne))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (nomCampagne == null) {
			if (other.nomCampagne != null)
				return false;
		} else if (!nomCampagne.equals(other.nomCampagne))
			return false;
		return true;
	}	
	
}
