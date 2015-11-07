package ru.ejbapp.DAO.jpa.entities;

import java.util.ArrayList;

import javax.ejb.Stateless;

import ru.ejbapp.DAO.interfaces.entities.IInsuranceDao;
import ru.ejbapp.DAO.jpa.GenericDaoJPA;
import ru.ejbapp.entities.Insurance;

@Stateless
public class InsurenceDaoJPA extends GenericDaoJPA<Insurance> implements IInsuranceDao {

	public InsurenceDaoJPA() {
		super(Insurance.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Insurance> getAll() {
		return (ArrayList<Insurance>) em.createQuery("FROM Insurance I").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Insurance> getAllOrderBy(String order) {
		return (ArrayList<Insurance>) em.createQuery("FROM Insurance I ORDER BY I." + order).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Insurance> getByParametrs(String params) {
		return (ArrayList<Insurance>) em.createQuery("FROM Insurance I " + params).getResultList();

	}
}