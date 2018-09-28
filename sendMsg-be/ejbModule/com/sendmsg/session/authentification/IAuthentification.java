package com.sendmsg.session.authentification;



import javax.ejb.Local;

import com.sendmsg.entities.authentification.SessionUtilisateur;
import com.sendmsg.entities.authentification.Utilisateur;


@Local
public interface IAuthentification {
	public void logout(Long sessionId);
	public SessionUtilisateur login(Long userId);
	public SessionUtilisateur findUserSessionById(Long id);
	public Utilisateur findUserById(Long id);
	public Utilisateur controlLogin(String login, String password);
	public void purgerSessions();
	public void changeMotPasse(Long userId, String newMdp);
	public Utilisateur addUser(Utilisateur utilisateur);

}
