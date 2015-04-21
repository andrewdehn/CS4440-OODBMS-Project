package edu.gatech.cs4440.spring2015.testing;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import edu.gatech.cs4440.spring2015.model.ComplexCar;

public class ObjectDBComplexTestModule implements ComplexDatabaseTestModule {
	
	String dbUrl;
	Map<String, String> properties;
	EntityManagerFactory emf;
	EntityManager em;

	public ObjectDBComplexTestModule() {
		dbUrl = "objectdb://localhost:6136/cs4440-project.tmp;drop";
		properties = new HashMap<String, String>();
		properties.put("javax.persistence.jdbc.user", "admin");
		properties.put("javax.persistence.jdbc.password", "admin");
	}

	@Override
	public String databaseName() {
		return "ObjectDBComplex";
	}

	@Override
	public void setup() throws DatabaseTestException {
		emf = Persistence.createEntityManagerFactory(dbUrl, properties);
		em = emf.createEntityManager();
	}

	@Override
	public void teardown() throws DatabaseTestException {
		em.close();
		emf.close();
	}

	@Override
	public void clean() throws DatabaseTestException {
		em.getTransaction().begin();
		em.createQuery("DELETE FROM Object").executeUpdate();
		em.getTransaction().commit();
	}

	@Override
	public void add(ComplexCar[] cars) throws DatabaseTestException {
		em.getTransaction().begin();
		for(int i = 0; i < cars.length; i++) {
			em.persist(cars[i]);
		}
		em.getTransaction().commit();
	}

	@Override
	public void update(ComplexCar[] cars) throws DatabaseTestException {
		em.getTransaction().begin();
		for(int i = 0; i < cars.length; i++) {
			ComplexCar retrieved = em.find(ComplexCar.class, cars[i].getVin());
			retrieved.setColor(cars[i].getColor());
			retrieved.setMake(cars[i].getMake());
			retrieved.setModel(cars[i].getModel());
			retrieved.setYear(cars[i].getYear());
			retrieved.setAccidents(cars[i].getAccidents());
			retrieved.setMaintenance(cars[i].getMaintenance());
		}
		em.getTransaction().commit();
	}

	@Override
	public void query(ComplexCar[] cars) throws DatabaseTestException {
		for(int i = 0; i < cars.length; i++) {
			ComplexCar retrieved = em.find(ComplexCar.class, cars[i].getVin());
			if(!cars[i].equals(retrieved)) {
				throw new DatabaseTestException("Retrieved object now equal to expected object |"
						+ "\n\tEXPECTED:  " + cars[i].toString() + "|" 
						+ "\n\tRETRIEVED: " + retrieved.toString());
			}
		}
	}

	@Override
	public void remove(ComplexCar[] cars) throws DatabaseTestException {
		em.getTransaction().begin();
		for(int i = 0; i < cars.length; i++) {
			ComplexCar retrieved = em.find(ComplexCar.class, cars[i].getVin());
			em.remove(retrieved);
		}
		em.getTransaction().commit();
	}

}
