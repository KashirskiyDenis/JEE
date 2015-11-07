package ru.ejbapp.DAO.interfaces;

import java.util.ArrayList;

public interface IGenericDao<T> {
	
	public void insert(T o);

	public void update(T o);

	public void delete(T o);

	public T getById(long id);

	public ArrayList<T> getAll();
	
}