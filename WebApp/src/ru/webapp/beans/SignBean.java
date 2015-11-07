package ru.webapp.beans;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import ru.ejbapp.Service;
import ru.ejbapp.entities.User;

@ManagedBean(name = "signin")
@SessionScoped
public class SignBean {

	@EJB
	private Service db;
	private String login;
	private String password;
	private User user;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String signIn() {
		if (user == null) {
			boolean bool = db.checkUser(login, password);
			if (bool) {
				user = db.getUserByLogin(login);
				if (user.getStatus() == 1)
					return user.getType().equalsIgnoreCase("user") ? "employee" : "users.xhtml?clear=true";
				else {
					user = null;
					return "infopage";
				}
			} else
				return "";
		} else if (user.getStatus() == 1)
			return user.getType().equalsIgnoreCase("user") ? "employee" : "users";
		else {
			user = null;
			return "infopage";
		}
	}

	public String exit() {
		setUser(null);
		return "index";
	}

	public String getTypeUser() {
		if (user == null)
			return null;
		if (user.getStatus() == 1)
			return user.getType();
		else
			return null;
	}
}