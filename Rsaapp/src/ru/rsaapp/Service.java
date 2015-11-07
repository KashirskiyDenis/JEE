package ru.rsaapp;

import java.util.ArrayList;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ru.rsaapp.entities.CoefTable;
import ru.rsaapp.interfaces.CoefTableDAO;

@Stateless
@LocalBean
public class Service implements CoefTableDAO {

	@PersistenceContext(name = "JPADB")
	private EntityManager em;
	
	public Service() { }
	
	@Override
	public CoefTable getById(long id) {
		return em.find(CoefTable.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<CoefTable> getAllRecords() {
		Query query = em.createQuery("FROM coefTable C");
		return (ArrayList<CoefTable>) query.getResultList();
	}
	
	public CoefTable getRecordByDLicense(long id) {
		Query query = em.createQuery("FROM coefTable C WHERE dlicense = :dLicense");
		query.setParameter("dLicense", id);
		return (CoefTable) query.getSingleResult();
	}

	@Override
	public void newRecord(CoefTable record) {
		em.persist(record);
	}
	
	@Override
	public void updateRecord(CoefTable record) {
		em.merge(record);
	}
	
	@Override
	public void deleteRecord(CoefTable record) {
		CoefTable r = em.merge(record);
		em.remove(r);
	}

	public CoefTable getByFio(String fio) {
		Query query = em.createNamedQuery("FROM coefTable C WHERE fio = :fio");
		query.setParameter("fio", "%" + fio + "%");
		return (CoefTable) query.getSingleResult();
	}

}
