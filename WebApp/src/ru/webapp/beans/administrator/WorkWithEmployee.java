package ru.webapp.beans.administrator;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import ru.ejbapp.Service;
import ru.ejbapp.entities.User;
import ru.webapp.beans.SignBean;

@ManagedBean(name = "awork")
@ViewScoped
public class WorkWithEmployee {

	private long id;
	private String tmp;
	private User user;
	@EJB
	private Service db;

	@ManagedProperty(value = "#{aindex}")
	private AllEmployees neededBean;

	@ManagedProperty(value = "#{signin}")
	private SignBean sb;
	
	@SuppressWarnings("unused")
	private boolean render;

	public void setSb(SignBean sb) {
		this.sb = sb;
	}
	
	public void setNeededBean(AllEmployees neededBean) {
		this.neededBean = neededBean;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTmp() {
		return tmp;
	}

	public void setTmp(String tmp) {
		this.tmp = tmp;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void init() {
		setTmp(id > 0 ? "Update" : "Create");
		user = (id > 0) ? (User) db.getById("User", id) : new User();
	}

	public String remove() {
		db.CRUD("delete", user);
		return "users";
	}

	public String create() {
		if (id > 0)
			db.CRUD("update", user);
		else
			db.CRUD("insert", user);
		neededBean.clear();
		return "users";
	}

	public boolean getDisplay() {
		return (id > 0) ? true : false;
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
			if (str.equals("user"))
				return false;
		}
		return true;
	}
}
