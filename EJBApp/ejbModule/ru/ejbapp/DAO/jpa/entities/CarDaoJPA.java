package ru.ejbapp.DAO.jpa.entities;

import java.util.ArrayList;

import javax.ejb.Stateless;

import ru.ejbapp.DAO.interfaces.entities.ICarDao;
import ru.ejbapp.DAO.jpa.GenericDaoJPA;
import ru.ejbapp.entities.Car;

@Stateless
public class CarDaoJPA extends GenericDaoJPA<Car> implements ICarDao {

	public CarDaoJPA() {
		super(Car.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Car> getAll() {
		return (ArrayList<Car>) em.createQuery("FROM Car C").getResultList();
	}

	@Override
	public Car getCarByNumber(String carNumber) {
		return (Car) em.createQuery("FROM Car C WHERE carNumber = :carNumber").setParameter("carNumber", carNumber)
				.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Car> getAllOrderBy(String order) {
		return (ArrayList<Car>) em.createQuery("FROM Car C ORDER BY C." + order).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Car> getByParametrs(String params) {
		return (ArrayList<Car>) em.createQuery("FROM Car C " + params).getResultList();

	}
}