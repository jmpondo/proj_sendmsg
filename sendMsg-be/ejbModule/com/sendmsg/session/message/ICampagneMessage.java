package com.sendmsg.session.message;

import java.util.List;

import javax.ejb.Local;

import com.sendmsg.entitie.message.Message;
import com.sendmsg.entities.recipient.Campagne;

@Local
public interface ICampagneMessage {

	public Campagne addCampagne(Campagne campagne, Message message);
	public Campagne updateCampagne(Campagne campagne);
	public Campagne getCampagneById(Long id);
	public void deleteCampagne(Long id);
	public List<Campagne> getCampagneList();
	
	
	public Message addCampagneMessage(Message message);
	public Message udpdateCampagneMessage(Message message);
	public Message getCampagneMessage(Long id);
	public void deleteCampagneMessage(Long id);
	public List<Message> getCampagneMessageList();
	
}
