package ru.rsaapp.beans;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import ru.rsaapp.Service;
import ru.rsaapp.entities.CoefTable;

@ManagedBean(name = "indexbean")
public class BeanForIndex {

	@EJB
	private Service db;
	private ArrayList<CoefTable> listOfRecord;

	public ArrayList<CoefTable> getListOfRecord() {
		listOfRecord = db.getAllRecords();		
		return listOfRecord;
	}

	public void setListOfRecord(ArrayList<CoefTable> listOfRecord) {
		this.listOfRecord = listOfRecord;
	}
}
