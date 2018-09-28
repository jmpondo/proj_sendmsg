package com.sendmsg.entitie.message;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="message")
public class Message implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(length=100)
	private String messageFr;
	@Column(length=100)
	private String messageEn;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getMessageFr() {
		return messageFr;
	}
	public void setMessageFr(String messageFr) {
		this.messageFr = messageFr;
	}
	public String getMessageEn() {
		return messageEn;
	}
	public void setMessageEn(String messageEn) {
		this.messageEn = messageEn;
	}
	public Message(Long id, String messageFr, String messageEn) {
		super();
		this.id = id;	
		this.messageFr = messageFr;
		this.messageEn = messageEn;
	}
	public Message() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
