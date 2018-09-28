package com.sendmsg.session.authentification;

import java.util.Date;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ValidationException;

import com.sendmsg.entities.authentification.SessionUtilisateur;
import com.sendmsg.entities.authentification.Utilisateur;


@TransactionAttribute(value=TransactionAttributeType.REQUIRED)
@Stateless
public class AuthentificationBE implements IAuthentification {
	@PersistenceContext(name="UP_senderMsg")
	private EntityManager em;
	@Override
	public void logout(Long sessionId) {
		SessionUtilisateur usersession=em.find(SessionUtilisateur.class, sessionId);
		usersession.setDateFin(new Date());
		
	}

	@Override
	public SessionUtilisateur findUserSessionById(Long id) {
		
		return em.find(SessionUtilisateur.class, id);
	}

	@Override
	public void purgerSessions() {			
		Query query = em.createQuery("update SessionUtilisateur set dateFin = ? where dateFin is null");
		query.setParameter(1, new Date());
		query.executeUpdate();
		
	}

	@Override
	public void changeMotPasse(Long userId, String newMdp) {
		Utilisateur user = em.find(Utilisateur.class, userId);
		user.setPassword(newMdp);	
		//user.setLastDateUpdate(new Date());
		
	}

	@Override
	public Utilisateur addUser(Utilisateur utilisateur) throws ValidationException{
		if(utilisateur == null)
			throw new ValidationException("Parametres utilisateur incorrect !");
		em.persist(utilisateur);
		return utilisateur;
	}

	
	@Override
	public SessionUtilisateur login(Long userId) {
		
			Utilisateur u=em.find(Utilisateur.class, userId);
			SessionUtilisateur usersession=new SessionUtilisateur();
			usersession.setDateDebut(new Date());
			usersession.setUtilisateur(u);;
			em.persist(usersession);
			return usersession;
		
	}

	@Override
	public Utilisateur controlLogin(String login, String password) {
		System.out.println(" ## JJ ## Dans controlLogin ## login = "+login);
		System.out.println(" ## JJ ## Dans controlLogin ## password = "+password);
		
		String jpql="select * from utilisateur u where u.login = :x and u.password = :y";
		
		System.out.println(" ## JJ ## Dans controlLogin ## Avant execute Query user:nbre enregistrement = ");
		//Query q = em.createQuery(jpql);
		Query q = em.createNativeQuery(jpql, Utilisateur.class);
		System.out.println(" ## JJ ## Dans controlLogin ## Après execute Query user:nbre enregistrement = ");
		q.setParameter("x", login);
		q.setParameter("y", password);
		System.out.println(" ## JJ ## Dans controlLogin ## Avant try user:nbre enregistrement = ");
		Utilisateur user = null;
		try {
				System.out.println(" ## JJ ## Dans controlLogin ## user:nbre enregistrement = "+q.getResultList().size());
				if(q.getResultList().size() > 0)
					user = (Utilisateur) q.getSingleResult();	
				else
					return null;
		}
		catch(Exception e) {
//				jpql="from Utilisateur u where u.login = :x";
//				q = em.createQuery(jpql);
//				q.setParameter("x", login);
//				Utilisateur user = null;
//						
//				return user;
				e.printStackTrace();
			}
		return user;
	}

	@Override
	public Utilisateur findUserById(Long id) {
		
		return em.find(Utilisateur.class, id);
	}		

}
