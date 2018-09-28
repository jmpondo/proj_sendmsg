package com.sendmsg.session.message;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ValidationException;

import com.sendmsg.entitie.message.GatewayParam;
import com.sendmsg.entitie.message.Notification;
import com.sendmsg.entities.authentification.Utilisateur;
import com.sendmsg.entities.recipient.Campagne;
import com.sendmsg.entities.recipient.Recipiendaire;

@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
@Stateless
public class NotificationSmsBE implements INotificationSms {
	@PersistenceContext(name="UP_senderMsg")
	private EntityManager em;
	
	
	@Override
	public Notification addNotification(Campagne campagne, Recipiendaire recipiendaire,
			GatewayParam gateway, Utilisateur utilisateur) {
		System.out.println(" ### Dans addNotification ####  ");
		Notification notification = new Notification();
		if(campagne == null)
			throw new ValidationException(" Campagne incorrect !");
		if(recipiendaire == null)
			throw new ValidationException(" Recipiendaire incorrect !");
		if(gateway == null)
			throw new ValidationException(" Gateway incorrect !");
		if(utilisateur == null)
			throw new ValidationException(" utilisateur inconnu !");
		notification.setCampagne(campagne);
		notification.setDateNotification(new Date());
		notification.setGateway(gateway);
		notification.setRecipiendaire(recipiendaire);
		notification.setUtilisateur(utilisateur);	
		em.persist(notification);
		
		return notification;
	}

	@Override
	public Notification udpateNotification(Notification notification) {
		if(notification == null)
			throw new ValidationException(" notification incorrecte !");
		return em.merge(notification);
	}

	@Override
	public void deleteNotification(Long id) {
		Notification n = em.find(Notification.class, id);
		em.remove(n);
		
	}

	@Override
	public Notification getNotificationById(Long id) {
		
		return em.find(Notification.class, id);
	}

	@Override
	public List<Notification> getNotificationList() {
		String jpql = "select * from notification";
		Query q = em.createNativeQuery(jpql, Notification.class);	
		if(q.getResultList().size() > 0) {
			return q.getResultList();
		}else 
		return null;
	}

	@Override
	public List<Notification> getNotificationListByCampagne(Campagne campagne) {
		String jpql = "select * from notification n where n.campagne = ?";
		Query q = em.createNativeQuery(jpql, Notification.class);	
		q.setParameter(1, campagne);
		if(q.getResultList().size() > 0) {
			return q.getResultList();
		}else 
		return null;
	}

	@Override
	public List<Notification> getNotificationListByRecipient(Recipiendaire recipiendaire) {
		String jpql = "select * from notification n where n.recipiendaire = ?";
		Query q = em.createNativeQuery(jpql, Recipiendaire.class);	
		q.setParameter(1, recipiendaire);
		if(q.getResultList().size() > 0) {
			return q.getResultList();
		}else 
		return null;	
	}

	@Override
	public List<Notification> getNotificationListByGateway(GatewayParam gateway) {
		String jpql = "select * from notification n where n.gateway = ?";
		Query q = em.createNativeQuery(jpql, Notification.class);		
		q.setParameter(1, gateway);
		if(q.getResultList().size() > 0) {
			return q.getResultList();
		}else 
		return null;
	}

	@Override
	public List<Notification> getNotificationListByUser(Utilisateur utilisateur) {
		String jpql = "select * from notification n where n.utilisateur = ?";
		Query q = em.createNativeQuery(jpql, Notification.class);			
		q.setParameter(1, utilisateur);
		
		if(q.getResultList().size() > 0) {
			return q.getResultList();
		}else 
		return null;
	}

	@Override
	public GatewayParam addGateway(GatewayParam gateway) {
		if(gateway == null)
			throw new ValidationException(" Gateway incorrect !");
		em.persist(gateway);
		return gateway;
	}

	@Override
	public GatewayParam updateGateway(GatewayParam gateway) {		
		return em.merge(gateway);
	}

	@Override
	public GatewayParam getGatewayById(Long id) {		
		return em.find(GatewayParam.class, id);
	}

	@Override
	public void deleteGateway(Long id) {
		GatewayParam g = em.find(GatewayParam.class, id);
		em.remove(g);
	}

	@Override
	public List<GatewayParam> getGatewayList() {
		String jpql = "select * from gateway";
		Query q = em.createNativeQuery(jpql, GatewayParam.class);	
		
		if(q.getResultList().size() > 0) {
			return q.getResultList();
		}
		else 
			return null;
	}

}
