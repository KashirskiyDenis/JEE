package ru.ejbapp.DAO.interfaces.entities;

import java.util.ArrayList;

import javax.ejb.Local;

import ru.ejbapp.DAO.interfaces.IGenericDao;
import ru.ejbapp.entities.Car;

@Local
public interface ICarDao extends IGenericDao<Car> {

	Car getCarByNumber(String carNumber);

	ArrayList<Car> getAllOrderBy(String order);

	ArrayList<Car> getByParametrs(String params);
}