package ru.rsaapp.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "coefTable")
public class CoefTable implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private long id;
	private int dLicense;
	private String fio;
	private double coef;

	public CoefTable() {
	}

	public CoefTable(long id, int dLicense, String fio, double coef) {
		this.id = id;
		this.dLicense = dLicense;
		this.fio = fio;
		this.coef = coef;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getDLicense() {
		return dLicense;
	}

	public void setDLicense(int dLicense) {
		this.dLicense = dLicense;
	}

	public String getFio() {
		return fio;
	}

	public void setFio(String fio) {
		this.fio = fio;
	}

	public double getCoef() {
		return coef;
	}

	public void setCoef(double coef) {
		this.coef = coef;
	}

	@Override
	public String toString() {
		return "#Id: " + id + " fio: " + fio + " ¹ driver's license: " + dLicense + " coeficient: " + coef;
	}
}
