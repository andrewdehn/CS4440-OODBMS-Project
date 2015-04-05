package edu.gatech.cs4440.spring2015.testing;

import edu.gatech.cs4440.spring2015.model.SimpleCar;

public class ObjectDBTestModule implements DatabaseTestModule {

	public ObjectDBTestModule() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String databaseName() {
		return "ObjectDB";
	}

	@Override
	public void setup() throws DatabaseTestException {
		// TODO Auto-generated method stub

	}

	@Override
	public void teardown() throws DatabaseTestException {
		// TODO Auto-generated method stub

	}

	@Override
	public void clean() throws DatabaseTestException {
		// TODO Auto-generated method stub

	}

	@Override
	public void add(SimpleCar[] cars) throws DatabaseTestException {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(SimpleCar[] cars) throws DatabaseTestException {
		// TODO Auto-generated method stub

	}

	@Override
	public void query(SimpleCar[] cars) throws DatabaseTestException {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(SimpleCar[] cars) throws DatabaseTestException {
		// TODO Auto-generated method stub

	}

}
