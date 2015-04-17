package edu.gatech.cs4440.spring2015.testing;

import edu.gatech.cs4440.spring2015.model.SimpleCar;

public class DummyTestModule implements DatabaseTestModule {

	public DummyTestModule() {
	}

	@Override
	public String databaseName() {
		return "DUMMY";
	}

	@Override
	public void setup() throws DatabaseTestException {

	}

	@Override
	public void teardown() throws DatabaseTestException {

	}

	@Override
	public void clean() throws DatabaseTestException {

	}

	@Override
	public void add(SimpleCar[] cars) throws DatabaseTestException {
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			throw new DatabaseTestException(e);
		}
	}

	@Override
	public void update(SimpleCar[] cars) throws DatabaseTestException {
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			throw new DatabaseTestException(e);
		}
	}

	@Override
	public void query(SimpleCar[] cars) throws DatabaseTestException {
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			throw new DatabaseTestException(e);
		}
	}

	@Override
	public void remove(SimpleCar[] cars) throws DatabaseTestException {
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			throw new DatabaseTestException(e);
		}
	}

}
