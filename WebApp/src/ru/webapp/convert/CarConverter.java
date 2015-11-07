package ru.webapp.convert;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import ru.ejbapp.Service;
import ru.ejbapp.entities.Car;

@ManagedBean
@FacesConverter("carConverter")
public class CarConverter implements Converter {

	@EJB
	private Service db;

	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				Car car = (Car) db.getById("Car", Long.parseLong(value));
				return car;
			} catch (NumberFormatException e) {
				throw new ConverterException(
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid car."));
			}
		} else
			return null;
	}

	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null)
			return String.valueOf(((Car) object).getId());
		else
			return null;
	}
}