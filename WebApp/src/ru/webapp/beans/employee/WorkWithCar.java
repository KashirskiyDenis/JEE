package ru.webapp.beans.employee;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import ru.ejbapp.Service;
import ru.ejbapp.entities.Car;
import ru.ejbapp.entities.Driver;
import ru.webapp.beans.SignBean;

@ManagedBean(name = "ecarwork")
@ViewScoped
public class WorkWithCar {

	private long id;
	private String tmp;
	private Car car;
	@EJB
	private Service db;

	@ManagedProperty(value = "#{allcars}")
	private AllCars neededBean;

	public void setNeededBean(AllCars neededBean) {
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

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	@SuppressWarnings("unchecked")
	public List<Driver> completeDriver(String query) {
		List<Driver> filteredDrivers = new ArrayList<Driver>();
		ArrayList<Driver> listOfDrivers = (ArrayList<Driver>) db.getAll("Driver");
		for (Driver driver : listOfDrivers)
			if (driver.getFio().startsWith(query))
				filteredDrivers.add(driver);

		return filteredDrivers;
	}

	public void init() {
		setTmp(id > 0 ? "Update" : "Create");
		car = (id > 0) ? (Car) db.getById("Car", id) : new Car();
	}

	public String remove() {
		db.CRUD("delete", car);
		return "cars";
	}

	public String create() {
		if (id > 0)
			db.CRUD("update", car);
		else
			db.CRUD("insert", car);
		neededBean.clear();
		return "cars";
	}

	public boolean getDisplay() {
		return (id > 0) ? true : false;
	}

	private SignBean sb;
	@SuppressWarnings("unused")
	private boolean render;
	
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
