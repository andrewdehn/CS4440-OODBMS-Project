package edu.gatech.cs4440.spring2015.testing;

import edu.gatech.cs4440.spring2015.model.SimpleCar;

/**
 * An interface that defines the database interactions used for testing 
 * database speed.
 * 
 * @author Andrew
 */
public interface DatabaseTestModule {

	/**
	 * Sets up and initalizes any resources and connections needed for the database.
	 * 
	 * @throws DatabaseTestException Action did not complete properly
	 */
	public void setup() throws DatabaseTestException;

	/**
	 * Tears down any resources and connections created by the implementation of 
	 * {@link DatabaseTestModule#setup()} or any other of the methods in 
	 * {@link DatabaseTestModule}.
	 * 
	 * @throws DatabaseTestException Action did not complete properly
	 */
	public void teardown() throws DatabaseTestException;
	
	/**
	 * Cleans the database to a prestine state so that methods of 
	 * {@link DatabaseTestModule} can execute without error.
	 * 
	 * @throws DatabaseTestException Action did not complete properly
	 */
	public void clean() throws DatabaseTestException;
	
	/**
	 * Adds the given cars to the database.
	 * @param cars
	 * 
	 * @throws DatabaseTestException Action did not complete properly
	 */
	public void add(SimpleCar[] cars) throws DatabaseTestException;
	
	/**
	 * Updates the given cars in the database.
	 * @param cars
	 * 
	 * @throws DatabaseTestException Action did not complete properly
	 */
	public void update(SimpleCar[] cars) throws DatabaseTestException;
	
	/**
	 * Query or retrieve the given cars from the database.
	 * @param cars
	 * 
	 * @throws DatabaseTestException Action did not complete properly
	 */
	public void query(SimpleCar[] cars) throws DatabaseTestException;
	
	/**
	 * Remove the given cars from the database.
	 * @param cars
	 * 
	 * @throws DatabaseTestException Action did not complete properly
	 */
	public void remove(SimpleCar[] cars) throws DatabaseTestException;

}
