package ru.webapp.beans.employee;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import ru.ejbapp.Service;
import ru.ejbapp.entities.Driver;

@ManagedBean(name = "alldrivers")
@SessionScoped
public class AllDrivers {

	@EJB
	private Service db;
	private ArrayList<Driver> listOfDriver;

	private String partFio;
	private int dLicense;
	private String order;
	private String clear;

	public ArrayList<Driver> getListOfDriver() {
		return listOfDriver;
	}

	public void setListOfDriver(ArrayList<Driver> listOfDriver) {
		this.listOfDriver = listOfDriver;
	}

	public String getPartFio() {
		return partFio;
	}

	public void setPartFio(String partFio) {
		this.partFio = partFio;
	}

	public int getdLicense() {
		return dLicense;
	}

	public void setdLicense(int dLicense) {
		this.dLicense = dLicense;
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

	@SuppressWarnings("unchecked")
	public void makeOrder() {
		listOfDriver = (ArrayList<Driver>) db.getAllOrderBy("Driver", order);
	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		if (partFio == null && dLicense == 0)
			listOfDriver = (ArrayList<Driver>) db.getAll("Driver");
	}

	@SuppressWarnings("unchecked")
	public String find() {
		boolean cPart = partFio.isEmpty();
		boolean cDL = dLicense == 0;

		if (cPart & cDL)
			listOfDriver = (ArrayList<Driver>) db.getAll("Driver");
		else if (!cPart & cDL)
			listOfDriver = (ArrayList<Driver>) db.findByParametrs("Driver", "WHERE fio LIKE '%" + partFio + "%'");
		else if (cPart & !cDL)
			listOfDriver = (ArrayList<Driver>) db.findByParametrs("Driver", "WHERE dLicense LIKE '%" + dLicense + "%'");
		else if (!cPart & !cDL)
			listOfDriver = (ArrayList<Driver>) db.findByParametrs("Driver",
					"WHERE fio LIKE '%" + partFio + "%'" + " AND " + "dLicense LIKE '%" + dLicense + "%'");

		return "";
	}
	
	public void clear() {
		partFio = null;
		order = null;
		dLicense = 0;
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
}
