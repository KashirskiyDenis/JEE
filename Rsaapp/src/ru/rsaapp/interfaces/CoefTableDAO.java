package ru.rsaapp.interfaces;

import java.util.ArrayList;

import javax.ejb.Local;

import ru.rsaapp.entities.CoefTable;

@Local
public interface CoefTableDAO {

	public CoefTable getById(long l);

	public ArrayList<CoefTable> getAllRecords();
	
	public void newRecord(CoefTable record);

	public void updateRecord(CoefTable record);

	public void deleteRecord(CoefTable record);
}
