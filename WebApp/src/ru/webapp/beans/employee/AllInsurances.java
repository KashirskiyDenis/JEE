package ru.webapp.beans.employee;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import ru.ejbapp.Service;
import ru.ejbapp.entities.Car;
import ru.ejbapp.entities.Insurance;
import ru.webapp.beans.SignBean;

@ManagedBean(name = "allinsurances")
@SessionScoped
public class AllInsurances {

	@EJB
	private Service db;
	private ArrayList<Insurance> listOfInsurance;

	private String carNumber;
	private String[] action;
	private String order;
	private String clear;

	public ArrayList<Insurance> getListOfInsurance() {
		return listOfInsurance;
	}

	public void setListOfInsurance(ArrayList<Insurance> listOfInsurance) {
		this.listOfInsurance = listOfInsurance;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public String[] getAction() {
		return action;
	}

	public void setAction(String[] action) {
		this.action = action;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		order = order.split(" ")[0];
		if (this.order != null) {
			String o1 = this.order.split(" ")[0];
			if (o1.equalsIgnoreCase(order))
				if (this.order.split(" ")[1].equalsIgnoreCase("ASC"))
					this.order = this.order.replace("ASC", "DESC");
				else
					this.order = this.order.replace("DESC", "ASC");
			else
				this.order = order + " ASC";
		} else
			this.order = order + " ASC";
		makeOrder();
	}

	public String getClear() {
		return clear;
	}

	public void setClear(String clear) {
		if (clear.equals("true"))
			clear();
		this.clear = clear;
	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		if (carNumber == null)
			listOfInsurance = (ArrayList<Insurance>) db.getAll("Insurance");
	}

	@SuppressWarnings("unchecked")
	public void makeOrder() {
		listOfInsurance = (ArrayList<Insurance>) db.getAllOrderBy("Insurance", order);
	}

	public void clear() {
		carNumber = null;
		order = null;
		action = null;
		init();
	}

	@SuppressWarnings("unchecked")
	public String find() {
		boolean cPart = carNumber.isEmpty();
		boolean cAction = cArray(action);

		if (cPart & cAction)
			listOfInsurance = (ArrayList<Insurance>) db.getAll("User");
		else if (!cPart & cAction) {
			Car car = db.getCarByNumber(carNumber);
			listOfInsurance = (ArrayList<Insurance>) db.findByParametrs("Insurance", "WHERE car_id = " + car.getId());
		} else if (cPart & !cAction)
			listOfInsurance = (ArrayList<Insurance>) db.findByParametrs("Insurance",
					"WHERE " + addWhere("number", "action", action));
		else {
			Car car = db.getCarByNumber(carNumber);
			listOfInsurance = (ArrayList<Insurance>) db.findByParametrs("Insurance",
					"WHERE " + addWhere("number", "action", action) + " AND car_id = " + car.getId());
		}

		return "";
	}

	private String addWhere(String type, String nameParam, String[] mas) {
		String str = "";
		for (String s : mas) {
			s = (type.equals("number")) ? s : "'" + s + "'";
			str += nameParam + " = " + s + " OR ";
		}
		return (str.length() == 0) ? str : "(" + str.substring(0, str.length() - 4) + ")";
	}

	private boolean cArray(Object[] o) {
		return (o.length == 0) ? true : false;
	}
	
	private boolean render = true;

	@ManagedProperty(value = "#{signin}")
	private SignBean sb;

	public void setSb(SignBean sb) {
		this.sb = sb;
	}
	
	public boolean getRender(){
		return checkAccess();
	}
	private boolean checkAccess() {
		if (sb == null)
			return false;
		else {
			String str = sb.getTypeUser();
			if (str == null)
				return false;	
		}
		return true;
	}
}
