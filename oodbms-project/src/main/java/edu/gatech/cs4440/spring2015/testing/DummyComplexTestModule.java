package edu.gatech.cs4440.spring2015.testing;

import edu.gatech.cs4440.spring2015.model.ComplexCar;

public class DummyComplexTestModule implements ComplexDatabaseTestModule {

	public DummyComplexTestModule() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String databaseName() {
		// TODO Auto-generated method stub
		return "DUMMY";
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
	public void add(ComplexCar[] cars) throws DatabaseTestException {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(ComplexCar[] cars) throws DatabaseTestException {
		// TODO Auto-generated method stub

	}

	@Override
	public void query(ComplexCar[] cars) throws DatabaseTestException {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(ComplexCar[] cars) throws DatabaseTestException {
		// TODO Auto-generated method stub

	}

}
