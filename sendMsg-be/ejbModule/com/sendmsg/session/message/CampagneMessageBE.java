package com.sendmsg.session.message;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ValidationException;

import com.sendmsg.entitie.message.Message;
import com.sendmsg.entities.recipient.Campagne;

@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
@Stateless
public class CampagneMessageBE implements ICampagneMessage {
	@PersistenceContext(name="UP_senderMsg")
	private EntityManager em;
	
	
	@Override
	public Campagne addCampagne(Campagne campagne, Message message) {
		if(message == null)
			throw new ValidationException(" Message campagne incorrect !");
		campagne.setMessage(message);
		em.persist(campagne);
		return campagne;
	}

	@Override
	public Campagne updateCampagne(Campagne campagne) {		
		return em.merge(campagne);
	}

	@Override
	public Campagne getCampagneById(Long id) {
		return em.find(Campagne.class, id);
	}

	@Override
	public void deleteCampagne(Long id) {
		Campagne c = em.find(Campagne.class, id);
		em.remove(c);
		
	}

	@Override
	public List<Campagne> getCampagneList() {
		String jpql = "select * from campagne";
		Query q = em.createNativeQuery(jpql, Campagne.class);	
		if(q.getResultList().size() > 0) {
			return q.getResultList();
		}else 
		return null;
	}

	@Override
	public Message addCampagneMessage(Message message) {
		if(message == null)
			throw new ValidationException(" Message de campagne incorrect !");
		em.persist(message);
		return message;
	}

	@Override
	public Message udpdateCampagneMessage(Message message) {
		
		return em.merge(message);
	}

	@Override
	public Message getCampagneMessage(Long id) {
	
		return em.find(Message.class, id);
	}

	@Override
	public void deleteCampagneMessage(Long id) {
		Message m = em.find(Message.class, id);
		em.remove(m);
		
	}

	@Override
	public List<Message> getCampagneMessageList() {
		String jpql = "select * from message";
		Query q = em.createNativeQuery(jpql, Message.class);	
		if(q.getResultList().size() > 0) {
			return q.getResultList();
		}else 
		return null;
	}	
}
