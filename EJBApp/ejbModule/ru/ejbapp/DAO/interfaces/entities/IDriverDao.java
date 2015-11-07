package ru.ejbapp.DAO.interfaces.entities;

import java.util.ArrayList;

import javax.ejb.Local;

import ru.ejbapp.DAO.interfaces.IGenericDao;
import ru.ejbapp.entities.Driver;

@Local
public interface IDriverDao extends IGenericDao<Driver> {

	ArrayList<Driver> getAllOrderBy(String order);

	ArrayList<Driver> getByParametrs(String params);

	// ArrayList<Driver> getListDriversByFIO(String fio);

	// Driver getDriverByFIO(String fio);
}
