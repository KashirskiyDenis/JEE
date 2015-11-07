package ru.ejbapp.DAO.jpa.entities;

import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.persistence.Query;

import ru.ejbapp.DAO.interfaces.entities.IUserDao;
import ru.ejbapp.DAO.jpa.GenericDaoJPA;
import ru.ejbapp.entities.User;

@Stateless
public class UserDaoJPA extends GenericDaoJPA<User> implements IUserDao {

	public UserDaoJPA() {
		super(User.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<User> getAll() {
		return (ArrayList<User>) em.createQuery("FROM User U").getResultList();
	}

	@SuppressWarnings("unused")
	@Override
	public boolean checkUser(String login, String password) {
		Query query = em.createQuery("SELECT u FROM User u WHERE u.login = :login AND u.password = :password");
		query.setParameter("login", login).setParameter("password", password);
		try {
			User user = (User) query.getSingleResult();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public User getUserByLogin(String login) {
		Query query = em.createQuery("SELECT u FROM User u WHERE u.login = :login");
		query.setParameter("login", login);
		return (User) query.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<User> getUserByPartLogin(String login) {
		Query query = em.createQuery("FROM User U WHERE login LIKE :login");
		query.setParameter("login", "%" + login + "%");
		return (ArrayList<User>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<User> getAllOrderBy(String order) {
		return (ArrayList<User>) em.createQuery("FROM User U ORDER BY U." + order).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<User> getByParametrs(String params) {
		return (ArrayList<User>) em.createQuery("FROM User U " + params).getResultList();
	}
}
