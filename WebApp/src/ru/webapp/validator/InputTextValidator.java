package ru.webapp.validator;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@ManagedBean
@FacesValidator(value = "ru.webapp.validator.InputTextValidator")
public class InputTextValidator implements Validator {
	@Override
	public void validate(FacesContext facesContext, UIComponent component, Object value) throws ValidatorException {
		String msg = "";
		String str = (value == null) ? "" : value.toString().trim();

		if (str.length() == 0) {
			msg = "Input incorrect value for " + component.getId();
			FacesMessage fm = new FacesMessage(msg, "Input[type=\"text\"] validation failed.");
			fm.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(fm);
		}
	}
}
