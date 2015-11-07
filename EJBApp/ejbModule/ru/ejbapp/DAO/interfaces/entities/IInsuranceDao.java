package ru.ejbapp.DAO.interfaces.entities;

import java.util.ArrayList;

import javax.ejb.Local;

import ru.ejbapp.DAO.interfaces.IGenericDao;
import ru.ejbapp.entities.Insurance;

@Local
public interface IInsuranceDao extends IGenericDao<Insurance> {

	ArrayList<Insurance> getAllOrderBy(String order);

	ArrayList<Insurance> getByParametrs(String params);

	// Insurance getInsuranceByIdCar(long idCar);
}