package ru.ejbapp.entities;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DRIVER")
public class Driver implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private long id;
	private String fio;
	private int dLicense;

	public Driver() {
	}

	public Driver(long id, String fio, int dLicense) {
		this.id = id;
		this.fio = fio;
		this.dLicense = dLicense;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFio() {
		return fio;
	}

	public void setFio(String fio) {
		this.fio = fio;
	}

	public int getdLicense() {
		return dLicense;
	}

	public void setdLicense(int dLicense) {
		this.dLicense = dLicense;
	}
	
	public String getFioInUTF8() {
		try {
			return new String(fio.getBytes("windows-1252"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return fio;
		}
	}

	@Override
	public String toString() {
		return "Id: " + id + ", FIO: " + fio + ", Number of driver license: " + dLicense;
	}
}