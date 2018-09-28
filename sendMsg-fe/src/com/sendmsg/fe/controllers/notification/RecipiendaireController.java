package com.sendmsg.fe.controllers.notification;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import com.sendmsg.entities.recipient.Recipiendaire;
import com.sendmsg.session.recipient.IRecipiendaire;

@ManagedBean(name="recipientView")
@ViewScoped
public class RecipiendaireController {
	
	@EJB
	private IRecipiendaire recip;
	private Recipiendaire recipiendaire = new Recipiendaire();
	private Recipiendaire selectedRecipiendaire;
	private List<Recipiendaire> recipiendaireList;	

	
	public Recipiendaire getRecipiendaire() {
		return recipiendaire;
	}

	public void setRecipiendaire(Recipiendaire recipiendaire) {
		this.recipiendaire = recipiendaire;
	}

	public Recipiendaire getSelectedRecipiendaire() {
		return selectedRecipiendaire;
	}

	public void setSelectedRecipiendaire(Recipiendaire selectedRecipiendaire) {
		this.selectedRecipiendaire = selectedRecipiendaire;
	}

	public List<Recipiendaire> getRecipiendaireList() {
		return recipiendaireList;
	}

	public void setRecipiendaireList(List<Recipiendaire> recipiendaireList) {
		this.recipiendaireList = recipiendaireList;
	}

	@PostConstruct
	public void init(){
		recipiendaireList = recip.getRecipients();
		//usersession= (SessionUtilisateur) ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getAttribute("currentSession");	
		
	}
	
	public void creerRecipiendaire() {
		
		RequestContext.getCurrentInstance().update("growl");
		FacesContext context = FacesContext.getCurrentInstance();
	
		if(recip.addRecipient(recipiendaire)!= null) {		
			refresh();			
			context.addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Creation effectuée avec succès !"));
		} else 
			context.addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Impossible de créer cette marque !"));
	
	}
	
	public void updateRecipiendaire() {
		
		RequestContext.getCurrentInstance().update("growl");
		FacesContext context = FacesContext.getCurrentInstance();
		Recipiendaire r = recip.getRecipient(selectedRecipiendaire.getId());
		r.setAspiration(selectedRecipiendaire.getAspiration());
		r.setCommune(selectedRecipiendaire.getCommune());
		r.setNom(selectedRecipiendaire.getNom());	
		r.setPrenom(selectedRecipiendaire.getPrenom());
		r.setTelephone(selectedRecipiendaire.getTelephone());
		if(recip.updateRecipient(r)!= null) {		
			refresh();			
			context.addMessage(r.getNom(), new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Modification effectuée avec succès !"));
		} else 
			context.addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Modification impossible !"));
	}
	
	public void removeRecipiendaire() {
		RequestContext.getCurrentInstance().update("growl");
		FacesContext context = FacesContext.getCurrentInstance();		
		if(selectedRecipiendaire != null) {
			recip.removeRecipient(selectedRecipiendaire.getId());					
			context.addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Suppression effectuée avec succès !"));
		} else 
			context.addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Suppression impossible !"));
	}
	
	public void refresh() {
	    FacesContext context = FacesContext.getCurrentInstance();
	    Application application = context.getApplication();
	    ViewHandler viewHandler = application.getViewHandler();
	    UIViewRoot viewRoot = viewHandler.createView(context, context
	     .getViewRoot().getViewId());
	    context.setViewRoot(viewRoot);
	    context.renderResponse(); //Optional
	}

}
