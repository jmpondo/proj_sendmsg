package com.sendmsg.fe.controllers.authentification;

import java.io.IOException;
import java.io.Serializable;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import com.sendmsg.entities.authentification.SessionUtilisateur;
import com.sendmsg.entities.authentification.Utilisateur;
import com.sendmsg.session.authentification.IAuthentification;





@ManagedBean(name="login")
@SessionScoped
public class AuthenticationController  implements Serializable{

@EJB
private IAuthentification auth;

private Utilisateur user;
private String username;
private String password;
private SessionUtilisateur usersession=new SessionUtilisateur();


public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public SessionUtilisateur getUsersession() {
	return usersession;
}


public void setUsersession(SessionUtilisateur usersession) {
	this.usersession = usersession;
}


	
public Utilisateur getUser() {
	return user;
}

public void setUser(Utilisateur user) {
	this.user = user;
}

public void controlLogin() throws IOException {
	user = auth.controlLogin(username, password);
	if(user != null) { 
	//	return "home";
		usersession = auth.login(user.getId()); 					
		((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true)).setAttribute("currentSession", usersession);					
		FacesContext.getCurrentInstance().getExternalContext().redirect("/sendMsg-fe/pages/dashboard.xhtml");
		
	} 	
	else 
	{
		RequestContext.getCurrentInstance().update("growl");
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(username, new FacesMessage(FacesMessage.SEVERITY_ERROR, "erreur", "Login ou mot de passe incorrect !"));
	   //	return "";	
	}	
	 
}

public void loginAction(ActionEvent event) throws IOException
{		 
	System.out.println(" ### Dans loginAction ### ");
	
	user = auth.controlLogin(username, password);

	if(user==null)
	{
		String message = "Login ou mot de passe incorrect !";
		FacesContext context = FacesContext.getCurrentInstance();
	    ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msgs");
	   // String message = bundle.getString("error_utilisateur_parametre_connexion");	    
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,message, ""));
	}
	else { 
			try {			
						
				System.out.println("#### Dans loginAction --JJ ds Else ### ");
				usersession = auth.login(user.getId()); 
					
				((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true)).setAttribute("currentSession", usersession);					
				FacesContext.getCurrentInstance().getExternalContext().redirect("/sendMsg-fe/pages/dashboard.xhtml");
				
			} catch (Exception e) {			
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
			}	
	   }
	}	

public void logoutAction(ActionEvent event)
{
	try{		
		((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).invalidate();
		FacesContext.getCurrentInstance().getExternalContext().redirect("/sendMsg-fe/");

	} catch (Exception e) {			
		e.printStackTrace();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
	}	

}
	
}


