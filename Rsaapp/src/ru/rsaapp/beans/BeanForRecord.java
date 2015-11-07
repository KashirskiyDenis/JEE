package ru.rsaapp.beans;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import ru.rsaapp.Service;
import ru.rsaapp.entities.CoefTable;

@ManagedBean(name = "recordbean")
@ViewScoped
public class BeanForRecord {

	private long id;
	private String tmp;
	private CoefTable record;
	@EJB
	private Service db;

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

	public CoefTable getRecord() {
		return record;
	}

	public void setRecord(CoefTable record) {
		this.record = record;
	}

	public void setDb(Service db) {
		this.db = db;
	}

	public void init() {
		setTmp(id > 0 ? "Update" : "Create");
		if (id > 0)
			record = db.getById(id);
		else
			record = new CoefTable();
	}

	public String remove() {
		db.deleteRecord(record);
		return "index";
	}

	public String create() {
		if (id > 0)
			db.updateRecord(record);
		else
			db.newRecord(record);
		return "index";
	}
}
