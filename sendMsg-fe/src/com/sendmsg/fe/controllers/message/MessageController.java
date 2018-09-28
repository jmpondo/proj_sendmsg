package com.sendmsg.fe.controllers.message;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.sendmsg.entitie.message.Message;
import com.sendmsg.session.message.ICampagneMessage;

@ManagedBean(name="mesgController")
@RequestScoped
public class MessageController {
	
	@EJB
	private ICampagneMessage campMsg;
	private Message message = new Message();

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public MessageController() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void creerMessage() {
		campMsg.addCampagneMessage(message);
	}
	

}
