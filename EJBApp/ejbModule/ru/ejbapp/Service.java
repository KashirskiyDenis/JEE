package ru.ejbapp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import ru.ejbapp.DAO.interfaces.entities.ICarDao;
import ru.ejbapp.DAO.interfaces.entities.IDriverDao;
import ru.ejbapp.DAO.interfaces.entities.IInsuranceDao;
import ru.ejbapp.DAO.interfaces.entities.IUserDao;
import ru.ejbapp.entities.Car;
import ru.ejbapp.entities.Driver;
import ru.ejbapp.entities.Insurance;
import ru.ejbapp.entities.User;

@Stateless(name = "service")
public class Service {

	@EJB
	private ICarDao carManager;
	@EJB
	private IDriverDao driverManager;
	@EJB
	private IInsuranceDao insuranceManager;
	@EJB
	private IUserDao userManager;

	public Service() {
	}

	public boolean checkUser(String login, String password) {
		return userManager.checkUser(login, password);
	}

	private double getCoefficientByIdDriver(long param) {
		HttpURLConnection urlConnection = null;
		BufferedReader reader = null;
		try {
			URL url = new URL("http://localhost:8080/Rsaapp/rest/resources/getByNumber/" + param);

			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.connect();

			InputStream inputStream = urlConnection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(inputStream));

			JsonReader jsonReader = Json.createReader(reader);
			JsonObject jsonObject = jsonReader.readObject();
			jsonReader.close();

			return jsonObject.getJsonNumber("coef").doubleValue();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return -1.0;
	}

	private Insurance calculateDriversAndPriceForInsurance(Insurance insurance) {
		int countDriver = insurance.getListOfDriver().size();
		insurance.setCountDriver(countDriver);
		List<Driver> listOfDrivers = insurance.getListOfDriver();
		double coefficient = 0.0;
		for (Driver driver : listOfDrivers) {
			double c = getCoefficientByIdDriver(driver.getdLicense());
			coefficient = (c > coefficient) ? c : coefficient;
		}
		int power = insurance.getCar().getPower();
		insurance.setPrice((3500 + power * coefficient) * 1.18);

		return insurance;
	}

	public void CRUD(String operation, Object o) {
		operation = operation.toUpperCase();
		Class<? extends Object> type = o.getClass();
		switch (operation) {
		case "INSERT":
			if (type == Car.class)
				carManager.insert((Car) o);
			else if (type == Driver.class)
				driverManager.insert((Driver) o);
			else if (type == Insurance.class)
				insuranceManager.insert(calculateDriversAndPriceForInsurance((Insurance) o));
			else if (type == User.class)
				userManager.insert((User) o);
			break;
		case "UPDATE":
			if (type == Car.class)
				carManager.update((Car) o);
			else if (type == Driver.class)
				driverManager.update((Driver) o);
			else if (type == Insurance.class)
				insuranceManager.update(calculateDriversAndPriceForInsurance((Insurance) o));
			else if (type == User.class)
				userManager.update((User) o);
			break;
		case "DELETE":
			if (type == Car.class)
				carManager.delete((Car) o);
			else if (type == Driver.class)
				driverManager.delete((Driver) o);
			else if (type == Insurance.class)
				insuranceManager.delete((Insurance) o);
			else if (type == User.class)
				userManager.delete((User) o);
			break;
		}
	}

	public ArrayList<? extends Object> getAll(String className) {
		switch (className) {
		case "Car":
			return carManager.getAll();
		case "Driver":
			return driverManager.getAll();
		case "Insurance":
			return insuranceManager.getAll();
		case "User":
			return userManager.getAll();
		}
		return null;
	}

	public Object getById(String className, long id) {
		switch (className) {
		case "Car":
			return carManager.getById(id);
		case "Driver":
			return driverManager.getById(id);
		case "Insurance":
			return insuranceManager.getById(id);
		case "User":
			return userManager.getById(id);
		}
		return null;
	}

	public User getUserByLogin(String login) {
		return userManager.getUserByLogin(login);
	}

	public ArrayList<? extends Object> getAllOrderBy(String className, String order) {
		switch (className) {
		case "Car":
			return carManager.getAllOrderBy(order);
		case "Driver":
			return driverManager.getAllOrderBy(order);
		case "Insurance":
			return insuranceManager.getAllOrderBy(order);
		case "User":
			return userManager.getAllOrderBy(order);
		}
		return null;
	}

	public ArrayList<User> findUsersByPartLogin(String partLogin) {
		return userManager.getUserByPartLogin(partLogin);
	}

	public ArrayList<? extends Object> findByParametrs(String className, String params) {
		switch (className) {
		case "Car":
			return carManager.getByParametrs(params);
		case "Driver":
			return driverManager.getByParametrs(params);
		case "Insurance":
			return insuranceManager.getByParametrs(params);
		case "User":
			return userManager.getByParametrs(params);
		}
		return null;		
	}

	public Car getCarByNumber(String carNumber) {
		return carManager.getCarByNumber(carNumber);
	}
}