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

import com.sendmsg.entitie.message.GatewayParam;
import com.sendmsg.session.param.IParametre;


@ManagedBean(name="gatewayView")
@ViewScoped
public class GatewayController {
	
	@EJB
	private IParametre param;
	private GatewayParam gateway = new GatewayParam();
	private GatewayParam selectedGateway;
	private List<GatewayParam> gatewayList;	

	public GatewayParam getGateway() {
		return gateway;
	}

	public void setGateway(GatewayParam gateway) {
		this.gateway = gateway;
	}

	public GatewayParam getSelectedGateway() {
		return selectedGateway;
	}

	public void setSelectedGateway(GatewayParam selectedGateway) {
		this.selectedGateway = selectedGateway;
	}

	public List<GatewayParam> getGatewayList() {
		return gatewayList;
	}

	public void setGatewayList(List<GatewayParam> gatewayList) {
		this.gatewayList = gatewayList;
	}

	@PostConstruct
	public void init(){
		gatewayList = param.listGateways();
		//usersession= (SessionUtilisateur) ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getAttribute("currentSession");	
		
	}
	
	public void creerGateway() {
		
		RequestContext.getCurrentInstance().update("growl");
		FacesContext context = FacesContext.getCurrentInstance();		
		
		if(param.addParam(gateway) != null) {		
			refresh();			
			context.addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Creation effectuée avec succès !"));
		} else 
			context.addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Impossible de créer cette marque !"));
	
	}
	
	public void updateGateway() {
		
		RequestContext.getCurrentInstance().update("growl");
		FacesContext context = FacesContext.getCurrentInstance();
		GatewayParam g = param.getGateway(selectedGateway.getId());
		
		g.setApiUrl(selectedGateway.getApiUrl());
		g.setCode(selectedGateway.getCode());
		g.setPassword(selectedGateway.getPassword());
		g.setUsername(selectedGateway.getUsername());
		
		if(param.updateParam(g) != null) {
		
			refresh();			
			context.addMessage(g.getCode(), new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Modification effectuée avec succès !"));
		} else 
			context.addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Modification impossible !"));
	}
	
	public void removeGateway() {
		RequestContext.getCurrentInstance().update("growl");
		FacesContext context = FacesContext.getCurrentInstance();		
		if(selectedGateway != null) {
			param.removeParam(selectedGateway.getId());					
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
