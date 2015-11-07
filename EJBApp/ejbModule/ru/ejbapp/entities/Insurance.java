package ru.ejbapp.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "INSURANCE_NEW")
public class Insurance implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@OneToOne
	private Car car;

	private int countDriver;
	private Date dateBegin;
	private Date dateEnd;
	private int action;
	private double price;

	@ManyToMany(fetch = FetchType.EAGER)
	private List<Driver> listOfDriver;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public int getCountDriver() {
		return countDriver;
	}

	public void setCountDriver(int countDriver) {
		this.countDriver = countDriver;
	}

	public Date getDateBegin() {
		return dateBegin;
	}

	public void setDateBegin(Date dateBegin) {
		this.dateBegin = dateBegin;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public List<Driver> getListOfDriver() {
		return listOfDriver;
	}

	public void setListOfDriver(ArrayList<Driver> listOfDriver) {
		this.listOfDriver = listOfDriver;
	}

	public String listOfDriverToString() {
		String str = "\n";
		for (Driver driver : listOfDriver)
			str += "\t<<" + driver.toString() + ">>\n";
		return str;
	}

	public String getListOfFioDrivers() {
		String str = "";
		for (Driver driver : listOfDriver)
			str += driver.getFio() + ", ";
		// System.out.println("String fio drivers:" + str);
		str = str.substring(0, str.length() - 2);
		return str;
	}

	public String getDateByFormat(int param) {
		switch (param) {
		case 1:
			return new SimpleDateFormat("dd-MM-yyyy").format(dateBegin).toString();
		case 2:
			return new SimpleDateFormat("dd-MM-yyyy").format(dateEnd).toString();
		default:
			return "dd-mm-yyyy";
		}
	}

	public String getActionAsString() {
		return (action == 1) ? "Yes" : "No";
	}

	public double getOrderPrice() {
        return new BigDecimal(price).setScale(2, RoundingMode.UP).doubleValue();
	}
	
	@Override
	public String toString() {
		return "Id: " + id + ", Car: <<" + car + ">>, \nCount drivers: " + countDriver + listOfDriverToString()
				+ ", Date begin: " + dateBegin + ", Date end: " + dateEnd + ", Action: " + (action == 1 ? "Yes" : "No")
				+ ", Price: " + price;
	}
}
