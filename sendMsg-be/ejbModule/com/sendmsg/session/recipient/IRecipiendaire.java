package com.sendmsg.session.recipient;

import java.util.List;

import javax.ejb.Local;

import com.sendmsg.entities.recipient.Campagne;
import com.sendmsg.entities.recipient.Recipiendaire;

@Local
public interface IRecipiendaire {
	public Recipiendaire addRecipient(Recipiendaire recipiendaire);
	public Recipiendaire updateRecipient(Recipiendaire recipiendaire);
	public void removeRecipient(Long id);
	public List<Recipiendaire> getRecipients();
	public Recipiendaire getRecipient(Long id);
	public Recipiendaire getRecipientByPhone(String telephone);
	public List<Campagne> getCampagnesByRecipient(Long recipiendaireId);
	
	
	public Campagne addCampagne(Campagne campagne);
	public Campagne updateCampagne(Campagne campagne);
	public void removeCampagne(Long id);
	public List<Recipiendaire> getRecipientsByCampagne(Long campagneId);
}
