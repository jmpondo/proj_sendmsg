package com.sendmsg.fe.controllers.message;

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
import com.sendmsg.entitie.message.Message;
import com.sendmsg.entities.recipient.Campagne;
import com.sendmsg.session.message.ICampagneMessage;

@ManagedBean(name="campController")
@ViewScoped
public class CampagneController {
	
	@EJB
	private ICampagneMessage campMsg;

	private Campagne campagne = new Campagne();
	private Message message = new Message();
	private List<Message> messages;
	private List<Campagne> campagneList;
	private Campagne selectedCampagne;
	
	
	private Long id;
	
	
	
	
	public List<Campagne> getCampagneList() {
		return campagneList;
	}
	public void setCampagneList(List<Campagne> campagneList) {
		this.campagneList = campagneList;
	}
	public Campagne getSelectedCampagne() {
		return selectedCampagne;
	}
	public void setSelectedCampagne(Campagne selectedCampagne) {
		this.selectedCampagne = selectedCampagne;
	}
	public List<Message> getMessages() {
		return messages;
	}
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Campagne getCampagne() {
		return campagne;
	}
	public void setCampagne(Campagne campagne) {
		this.campagne = campagne;
	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	public CampagneController() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@PostConstruct
	public void init(){
		campagneList = campMsg.getCampagneList();
		//usersession= (SessionUtilisateur) ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getAttribute("currentSession");	
		
	}
	
	public void creerCampagne() {
		
		RequestContext.getCurrentInstance().update("growl");
		FacesContext context = FacesContext.getCurrentInstance();
	
		if(campMsg.addCampagne(campagne, message) != null) {		
			refresh();			
			context.addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Creation effectuée avec succès !"));
		} else 
			context.addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Impossible de créer cette marque !"));
	
	}
	
	public void updateCampagne() {
		
		RequestContext.getCurrentInstance().update("growl");
		FacesContext context = FacesContext.getCurrentInstance();
		Campagne c = campMsg.getCampagneById(selectedCampagne.getId());
		c.setMessage(message);	
		if(campMsg.updateCampagne(c) != null) {		
			refresh();			
			context.addMessage(c.getNomCampagne(), new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Modification effectuée avec succès !"));
		} else 
			context.addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Modification impossible !"));
	}
	
	public void removeCampagne() {
		RequestContext.getCurrentInstance().update("growl");
		FacesContext context = FacesContext.getCurrentInstance();		
		if(selectedCampagne != null) {
			campMsg.deleteCampagne(selectedCampagne.getId());			
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
