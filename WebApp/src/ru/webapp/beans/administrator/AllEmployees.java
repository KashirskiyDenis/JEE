package ru.webapp.beans.administrator;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import ru.ejbapp.Service;
import ru.ejbapp.entities.User;
import ru.webapp.beans.SignBean;

@ManagedBean(name = "aindex")
@SessionScoped
public class AllEmployees {

	@EJB
	private Service db;
	private ArrayList<User> listOfEmployee;

	private String partLogin;
	private String order;
	private String[] status;
	private String[] type;

	private String clear;
	private boolean render = true;

	@ManagedProperty(value = "#{signin}")
	private SignBean sb;

	public void setSb(SignBean sb) {
		this.sb = sb;
	}
	
	public ArrayList<User> getListOfEmployee() {
		return listOfEmployee;
	}

	public void setListOfEmployee(ArrayList<User> listOfEmployee) {
		this.listOfEmployee = listOfEmployee;
	}

	public String getPartLogin() {
		return partLogin;
	}

	public void setPartLogin(String partLogin) {
		this.partLogin = partLogin;
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

	public String[] getStatus() {
		return status;
	}

	public void setStatus(String[] status) {
		this.status = status;
	}

	public String[] getType() {
		return type;
	}

	public void setType(String[] type) {
		this.type = type;
	}

	public boolean getRender(){
		return checkAccess();
	}
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		if (partLogin == null & render)
			listOfEmployee = (ArrayList<User>) db.getAll("User");
	}
	
	@SuppressWarnings("unchecked")
	public void makeOrder() {
		listOfEmployee = (ArrayList<User>) db.getAllOrderBy("User", order);
	}

	@SuppressWarnings("unchecked")
	public String find() {
		boolean cPart = partLogin.isEmpty();
		boolean cStatus = cArray(status);
		boolean cType = cArray(type);

		if (cPart & cStatus & cType)
			listOfEmployee = (ArrayList<User>) db.getAll("User");
		else if (!cPart & cStatus & cType)
			listOfEmployee = (ArrayList<User>) db.findUsersByPartLogin(partLogin);
		else if (cPart & !cStatus & cType)
			listOfEmployee = (ArrayList<User>) db.findByParametrs("User", "WHERE " + addWhere("number", "status", status));
		else if (cPart & cStatus & !cType)
			listOfEmployee = (ArrayList<User>) db.findByParametrs("User", "WHERE " + addWhere("string", "type", type));
		else if (!cPart & !cStatus & cType)
			listOfEmployee = (ArrayList<User>) db.findByParametrs("User", 
					"WHERE " + addWhere("number", "status", status) + " AND login LIKE '" + partLogin + "%'");
		else if (!cPart & cStatus & !cType)
			listOfEmployee = (ArrayList<User>) db.findByParametrs("User", 
					"WHERE " + addWhere("string", "type", type) + " AND login LIKE '" + partLogin + "%'");
		else if (cPart & !cStatus & !cType)
			listOfEmployee = (ArrayList<User>) db.findByParametrs("User", 
					"WHERE " + addWhere("number", "status", status) + " AND " + addWhere("string", "type", type));
		else
			listOfEmployee = (ArrayList<User>) db.findByParametrs("User", "WHERE " + addWhere("number", "status", status)
					+ " AND " + addWhere("string", "type", type) + " AND login LIKE '" + partLogin + "%'");
		
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

	public void clear() {
		partLogin = null;
		order = null;
		status = null;
		type = null;
		init();
	}

	public String getClear() {
		return clear;
	}

	public void setClear(String clear) {
		if (clear.equals("true"))
			clear();
		this.clear = clear;
	}

	private boolean checkAccess() {
		if (sb == null)
			return false;
		else {
			String str = sb.getTypeUser();
			if (str == null)
				return false;
			if (str.equals("user"))
				return false;
		}
		return true;
	}
}
