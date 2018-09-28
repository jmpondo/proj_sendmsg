package com.sendmsg.session.recipient;


import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ValidationException;

import com.sendmsg.entitie.message.Notification;
import com.sendmsg.entities.recipient.Campagne;
import com.sendmsg.entities.recipient.Recipiendaire;

@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
@Stateless
public class RecipiendaireBE implements IRecipiendaire {
	@PersistenceContext(name="UP_senderMsg")
	private EntityManager em;
	
	@Override
	public Recipiendaire addRecipient(Recipiendaire recipiendaire) {
		if(recipiendaire == null)
			throw new ValidationException(" Destinataire incorrect");
		em.persist(recipiendaire);
		return recipiendaire;
	}

	@Override
	public Recipiendaire updateRecipient(Recipiendaire recipiendaire) {
		
		return em.merge(recipiendaire);
	}

	@Override
	public void removeRecipient(Long id) {
		Recipiendaire d = em.find(Recipiendaire.class, id);
		em.remove(d);
	}

	@Override
	public List<Recipiendaire> getRecipients() {
		String jpql = "select * from recipiendaire";
		Query q = em.createNativeQuery(jpql, Recipiendaire.class);
		if(q.getResultList().size() > 0) {
			return q.getResultList();
		}else 
		return null;
	}

	@Override
	public Recipiendaire getRecipient(Long id) {
	
		return em.find(Recipiendaire.class, id);
	}

	@Override
	public Recipiendaire getRecipientByPhone(String telephone) {
		String jpql = "select * from recipiendaire r where r.telephone = ?";
		Query q = em.createNativeQuery(jpql, Recipiendaire.class);	
		q.setParameter(1, telephone);
		if(q.getResultList().size() > 0)
			return (Recipiendaire) q.getSingleResult();
		else return null;
				
	}

	@Override
	public List<Campagne> getCampagnesByRecipient(Long recipiendaireId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Campagne addCampagne(Campagne campagne) {
		if( campagne == null)
			throw new ValidationException(" Campagne incorrecte ! ");
		em.persist(campagne);
		return campagne;
	}

	@Override
	public Campagne updateCampagne(Campagne campagne) {
		if( campagne == null)
			throw new ValidationException(" Campagne incorrecte ! ");
		return em.merge(campagne);
	}

	@Override
	public void removeCampagne(Long id) {
		Campagne c = em.find(Campagne.class, id);
		em.remove(c);
		
	}

	@Override
	public List<Recipiendaire> getRecipientsByCampagne(Long campagneId) {	
		List<Notification> nList = null; List<Recipiendaire> recipiendaires = new ArrayList<Recipiendaire>();
		Campagne c = em.find(Campagne.class, campagneId);
		String jpql = "select * from notification n where n.campagne = ?";
		Query q = em.createNativeQuery(jpql, Recipiendaire.class);
		q.setParameter(1, c);
		nList = q.getResultList();
		
		if(nList.size() != 0) {
			for(int i=0; i < nList.size(); i++) 
			{
				Recipiendaire r = nList.get(i).getRecipiendaire();
				recipiendaires.add(new Recipiendaire(r.getId(), r.getTelephone(), r.getNom(), r.getPrenom(), r.getCommune(), r.getAspiration()));
			}
		}
		
		return recipiendaires;		
	}

}
