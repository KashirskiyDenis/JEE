package ru.ejbapp.DAO.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ru.ejbapp.DAO.interfaces.IGenericDao;

public abstract class GenericDaoJPA<T> implements IGenericDao<T> {

	private Class<T> type;

	@PersistenceContext
	protected EntityManager em;

	public GenericDaoJPA() {
	}

	public GenericDaoJPA(Class<T> type) {
		this.type = type;
	}

	@Override
	public void insert(T o) {
		em.persist(o);
	}

	@Override
	public void update(T o) {
		em.merge(o);
	}

	@Override
	public void delete(T o) {
		T oo = em.merge(o);
		em.remove(oo);
	}

	@Override
	public T getById(long id) {
		return em.find(type, id);
	}

}