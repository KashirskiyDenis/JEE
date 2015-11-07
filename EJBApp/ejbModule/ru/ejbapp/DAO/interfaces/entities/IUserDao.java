package ru.ejbapp.DAO.interfaces.entities;

import java.util.ArrayList;

import javax.ejb.Local;

import ru.ejbapp.DAO.interfaces.IGenericDao;
import ru.ejbapp.entities.User;

@Local
public interface IUserDao extends IGenericDao<User> {

	boolean checkUser(String login, String password);
	
	User getUserByLogin(String login);

	ArrayList<User> getAllOrderBy(String order);

	ArrayList<User> getUserByPartLogin(String partLogin);

	ArrayList<User> getByParametrs(String params);
}
