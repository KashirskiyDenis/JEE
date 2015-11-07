package ru.ejbapp.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private long id;
	private String login;
	private String password;
	private String type;
	private int status;

	public User() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getStatusToString() {
		return (status == 1 ? "Unlocked" : "Locked");
	}
	
	public String getFullType(){
		return  (type.equals("user") ? "Employee" : "Administrator");
	}	

	@Override
	public String toString() {
		return "Id: " + id + ", Login: " + login + ", Password: " + password.getBytes() + ", Type: " + type
				+ ", Status: " + (status == 1 ? "unlocked" : "locked");
	}

}