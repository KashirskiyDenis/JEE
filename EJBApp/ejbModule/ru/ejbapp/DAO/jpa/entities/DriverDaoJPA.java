package ru.ejbapp.DAO.jpa.entities;

import java.util.ArrayList;

import javax.ejb.Stateless;

import ru.ejbapp.DAO.interfaces.entities.IDriverDao;
import ru.ejbapp.DAO.jpa.GenericDaoJPA;
import ru.ejbapp.entities.Driver;

@Stateless
public class DriverDaoJPA extends GenericDaoJPA<Driver> implements IDriverDao {

	public DriverDaoJPA() {
		super(Driver.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Driver> getAll() {
		return (ArrayList<Driver>) em.createQuery("FROM Driver D").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Driver> getAllOrderBy(String order) {
		return (ArrayList<Driver>) em.createQuery("FROM Driver D ORDER BY D." + order).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Driver> getByParametrs(String params) {
		return (ArrayList<Driver>) em.createQuery("FROM Driver D " + params).getResultList();
	}

}
