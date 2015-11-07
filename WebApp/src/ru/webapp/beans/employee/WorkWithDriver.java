package ru.webapp.beans.employee;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import ru.ejbapp.Service;
import ru.ejbapp.entities.Driver;
import ru.webapp.beans.SignBean;

@ManagedBean(name = "edriverwork")
@ViewScoped
public class WorkWithDriver {

	private long id;
	private String tmp;
	private Driver driver;
	@EJB
	private Service db;

	@ManagedProperty(value = "#{alldrivers}")
	private AllDrivers neededBean;

	public void setNeededBean(AllDrivers neededBean) {
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

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public void init() {
		setTmp(id > 0 ? "Update" : "Create");
		driver = (id > 0) ? (Driver) db.getById("Driver", id) : new Driver();
	}

	public String remove() {
		db.CRUD("delete", driver);
		return "drivers";
	}

	public String create() {
		if (id > 0)
			db.CRUD("update", driver);
		else
			db.CRUD("insert", driver);
		neededBean.clear();
		return "drivers";
	}

	public boolean getDisplay() {
		return (id > 0) ? true : false;
	}

	@ManagedProperty(value = "#{signin}")
	private SignBean sb;

	public void setSb(SignBean sb) {
		this.sb = sb;
	}

	@SuppressWarnings("unused")
	private boolean render;

	public boolean getRender() {
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
