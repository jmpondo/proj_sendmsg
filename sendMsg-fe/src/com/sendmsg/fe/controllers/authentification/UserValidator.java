package com.sendmsg.fe.controllers.authentification;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;


@FacesValidator("c2c.validator.UserValidator")
public class UserValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {

		
		String mdp = (String) value;
		if (mdp != null && !mdp.equals("")) {
			if (!mdp.matches("(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{6,15})$") ) {
				ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msgs");
				FacesMessage msg = new FacesMessage("invalid.password", bundle.getString("invalid_password"));
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);
			}
		}
	}

}
