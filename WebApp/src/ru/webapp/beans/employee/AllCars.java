package ru.webapp.beans.employee;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import ru.ejbapp.Service;
import ru.ejbapp.entities.Car;
import ru.webapp.beans.SignBean;

@ManagedBean(name = "allcars")
@SessionScoped
public class AllCars {
	@EJB
	private Service db;
	private ArrayList<Car> listOfCar;

	private String carNumber;
	private int minPower;
	private int maxPower = 10000;
	private String order;
	private String clear;

	public ArrayList<Car> getListOfCar() {
		return listOfCar;
	}

	public void setListOfCar(ArrayList<Car> listOfCar) {
		this.listOfCar = listOfCar;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public int getMinPower() {
		return minPower;
	}

	public void setMinPower(int minPower) {
		this.minPower = minPower;
	}

	public int getMaxPower() {
		return maxPower;
	}

	public void setMaxPower(int maxPower) {
		this.maxPower = maxPower;
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
	public void makeOrder() {
		listOfCar = (ArrayList<Car>) db.getAllOrderBy("Car", order);
	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		if (carNumber == null)
			listOfCar = (ArrayList<Car>) db.getAll("Car");
	}

	public void clear() {
		carNumber = null;
		minPower = 0;
		maxPower = 0;
		order = null;
		init();
	}

	@SuppressWarnings("unchecked")
	public String find() {
		boolean cCN = carNumber.isEmpty();

		if (!cCN)
			listOfCar = (ArrayList<Car>) db.findByParametrs("Car",
					"WHERE carNumber LIKE '%" + carNumber + "%' AND power BETWEEN " + minPower + " AND " + maxPower);
		else
			listOfCar = (ArrayList<Car>) db.findByParametrs("Car",
					"WHERE power BETWEEN " + minPower + " AND " + maxPower);
		return "";
	}

	private boolean render;

	@ManagedProperty(value = "#{signin}")
	private SignBean sb;

	public void setSb(SignBean sb) {
		this.sb = sb;
	}

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
