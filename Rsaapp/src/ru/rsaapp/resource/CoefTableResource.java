package ru.rsaapp.resource;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import ru.rsaapp.Service;
import ru.rsaapp.entities.CoefTable;

@Path("/resources")
public class CoefTableResource {
	
	@EJB
	private Service db;
	
	@GET
	@Path("getAll")
	@Produces("application/json")
	public Response getAllRecords() {
		ArrayList<CoefTable> records = db.getAllRecords();
		if (records == null) {
			throw new RuntimeException("Can't load all records");
		}
		return Response.status(200).entity(records).build();
	}
	
	@GET
	@Path("sayHello")
	public String sayHello() {
		return "Hello: ";
	}	
	
	@GET
	@Path("getByNumber/{param}")
	@Produces("application/json")
	public Response getByNumber(@PathParam("param") String msg) {
		long id = Long.parseLong(msg);
		CoefTable record = db.getRecordByDLicense(id);
		Response res = Response.status(200).entity(record).build();
		return res;
	}
	
	@GET
	@Path("getByFio/{param}")
	@Produces("application/json")
	public Response getByFio(@PathParam("param") String msg) {
		CoefTable record = db.getByFio(msg);
		Response res = Response.status(200).entity(record).build();
		return res;
	}		
	/*
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_XML)
	public CoefTable getRecordById(@PathParam("id") long id) {
		CoefTable record = db.getById(id);
		if (record == null) {
			throw new RuntimeException("Can't find record with id = " + id);
		}
		return record;
	}

	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public void addRecord(JAXBElement<CoefTable> record) {
		try {
			db.newRecord(record.getValue());
		} catch (Exception e) {
			throw new RuntimeException("Can't add record with id = " + record.getValue().getId());
		}
	}

	@DELETE
	@Path("{id}")
	public void deleteRecord(@PathParam("id") long id) {
		try {
			db.deleteRecord(db.getById(id));
		} catch (Exception e) {
			throw new RuntimeException("Can't delete record with id = " + id);
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public void updateRecord(JAXBElement<CoefTable> record) {
		try {
			db.updateRecord(record.getValue());
		} catch (Exception e) {
			throw new RuntimeException("Can't update record with id = " + record.getValue().getId());
		}
	}
	*/
}