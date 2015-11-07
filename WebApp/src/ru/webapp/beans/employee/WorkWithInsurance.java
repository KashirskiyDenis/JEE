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
import ru.ejbapp.entities.Insurance;
import ru.webapp.beans.SignBean;

@ManagedBean(name = "einsurancework")
@ViewScoped
public class WorkWithInsurance {

	private long id;
	private String tmp;
	private Insurance insurance;
	@EJB
	private Service db;

	@ManagedProperty(value = "#{allinsurances}")
	private AllInsurances neededBean;

	public void setNeededBean(AllInsurances neededBean) {
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

	public Insurance getInsurance() {
		return insurance;
	}

	public void setInsurance(Insurance insurances) {
		this.insurance = insurances;
	}

	@SuppressWarnings("unchecked")
	public List<Driver> completeDrivers(String query) {
		ArrayList<Driver> allDrivers = (ArrayList<Driver>) db.getAll("Driver");
		List<Driver> filteredDrivers = new ArrayList<Driver>();

		for (Driver driver : allDrivers)
			if (driver.getFio().startsWith(query))
				filteredDrivers.add(driver);

		return filteredDrivers;
	}

	@SuppressWarnings("unchecked")
	public List<Car> completeCar(String query) {
		ArrayList<Car> allCars = (ArrayList<Car>) db.getAll("Car");
		List<Car> filteredCars = new ArrayList<Car>();

		for (Car car : allCars)
			if (car.getCarNumber().startsWith(query))
				filteredCars.add(car);

		return filteredCars;
	}

	public void init() {
		setTmp(id > 0 ? "Update" : "Create");
		insurance = (id > 0) ? (Insurance) db.getById("Insurance", id) : new Insurance();
	}

	public String remove() {
		db.CRUD("delete", insurance);
		return "insurances";
	}

	public String create() {
		if (id > 0)
			db.CRUD("update", insurance);
		else
			db.CRUD("insert", insurance);
		neededBean.clear();
		return "insurances";
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
