package com.sendmsg.fe.component.tools;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

public class AuthorizedListener implements PhaseListener {

	@Override
	public void afterPhase(PhaseEvent event) {
		FacesContext facesContext = event.getFacesContext();
		String currentPage = facesContext.getViewRoot().getViewId();
		boolean isLoginPage = (currentPage.lastIndexOf("index.xhtml") > -1);
		HttpSession session = (HttpSession)facesContext.getExternalContext().getSession(false);
		
		if(session == null) {
			System.out.println(" No, I'm not there");
			NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
			navigationHandler.handleNavigation(facesContext, null, "/index.xhtml");
		} 
		else {
				Object currentUser = session.getAttribute("currentSession");
				System.out.println(" Yes, I'm there");
				if(!isLoginPage && (currentUser == null || currentUser =="")) {
					System.out.println(" Yes, I'm there, inside");
					NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
					navigationHandler.handleNavigation(facesContext, null, "/index.xhtml");
				}
				facesContext.getApplication().getNavigationHandler();
		}
		
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PhaseId getPhaseId() {
		
		return 	PhaseId.RESTORE_VIEW;
	}

}
