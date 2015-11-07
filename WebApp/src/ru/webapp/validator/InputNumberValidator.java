package ru.webapp.validator;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@ManagedBean
@FacesValidator(value = "ru.webapp.validator.InputNumberValidator")
public class InputNumberValidator implements Validator {
	@Override
	public void validate(FacesContext facesContext, UIComponent component, Object value) throws ValidatorException {
		String msg = "";
		String str = (value == null) ? "" : value.toString().trim();
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException e) {
			msg = "Input incorrect value for " + component.getId() + " " + e.getMessage();
			FacesMessage fm = new FacesMessage(msg, "Input[type=\"number\"] validation failed.");
			fm.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(fm);
		}
	}
}
