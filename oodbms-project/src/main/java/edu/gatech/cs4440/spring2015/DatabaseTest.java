package edu.gatech.cs4440.spring2015;

import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import edu.gatech.cs4440.spring2015.generator.SimpleCarGenerator;
import edu.gatech.cs4440.spring2015.model.SimpleCar;
import edu.gatech.cs4440.spring2015.testing.DatabaseTestException;
import edu.gatech.cs4440.spring2015.testing.DatabaseTestModule;

public class DatabaseTest {

	int prestart;
	int numTests;
	int numObjects;
	DatabaseTestModule dbTestModule;
	
	public DatabaseTest(int prestart, int numTests, int numObjects, DatabaseTestModule dbTestModule) {
		this.prestart = prestart;
		this.numTests = numTests;
		this.numObjects = numObjects;
		this.dbTestModule = dbTestModule;
	}
	
	public void test() {
		SimpleCarGenerator simpleCarGenerator = new SimpleCarGenerator();
		SimpleCar[] simpleCars = simpleCarGenerator.generate(numObjects);
		
		int numExceptions = 0;
		long time1, time2;
		
		boolean addTestSuccess = true;
		boolean updateTestSuccess = true;
		boolean queryTestSuccess = true;
		boolean removeTestSuccess = true;
		
		double[] addTimes = new double[numTests];
		double[] updateTimes = new double[numTests];
		double[] queryTimes = new double[numTests];
		double[] removeTimes = new double[numTests];
		
		try {
			dbTestModule.setup();
		} catch (DatabaseTestException e) {
			System.out.println("Error setting up database " + dbTestModule.databaseName());
			e.printStackTrace();
		}
		
		System.out.println("Running add tests for " + dbTestModule.databaseName());
		numExceptions = 0;
		for(int i = 0; i < numTests; i++) {
			for(int j = 0; j < prestart; j++) {
				try {
					dbTestModule.clean();
					dbTestModule.add(simpleCars);
				} catch (DatabaseTestException e) {
					
				}
			}
			
			if(numExceptions >= 3) {
				System.out.println(numExceptions  + " exceptions occured, aborting add tests");
				addTestSuccess = false;
				break;
			}
			
			try {
				dbTestModule.clean();
				
				time1 = System.nanoTime();
				dbTestModule.add(simpleCars);
				time2 = System.nanoTime();
				
				addTimes[i] = (((double) time2) - ((double) time1)) * 1e-6;
				System.out.println("add test " + i + ": " + addTimes[i]);
			} catch (DatabaseTestException e) {
				System.out.println("Exception during " + dbTestModule.databaseName() + " add testing");
				e.printStackTrace();
				numExceptions++;
				i--;
			}
		}
		
		
		System.out.println("Running update tests for " + dbTestModule.databaseName());
		numExceptions = 0;
		for(int i = 0; i < numTests; i++) {
			for(int j = 0; j < prestart; j++) {
				try {
					dbTestModule.clean();
					dbTestModule.add(simpleCars);
					dbTestModule.update(simpleCars);
				} catch (DatabaseTestException e) {
					
				}
			}
			
			if(numExceptions >= 3) {
				System.out.println(numExceptions  + " exceptions occured, aborting update tests");
				updateTestSuccess = false;
				break;
			}
			
			try {
				dbTestModule.clean();
				dbTestModule.add(simpleCars);
				
				time1 = System.nanoTime();
				dbTestModule.update(simpleCars);
				time2 = System.nanoTime();
				
				updateTimes[i] = (((double) time2) - ((double) time1)) * 1e-6;
				System.out.println("update test " + i + ": " + updateTimes[i]);
			} catch (DatabaseTestException e) {
				System.out.println("Exception during " + dbTestModule.databaseName() + " update testing");
				e.printStackTrace();
				numExceptions++;
				i--;
			}
		}
		
		System.out.println("Running query tests for " + dbTestModule.databaseName());
		numExceptions = 0;
		for(int i = 0; i < numTests; i++) {
			for(int j = 0; j < prestart; j++) {
				try {
					dbTestModule.clean();
					dbTestModule.add(simpleCars);
					dbTestModule.query(simpleCars);
				} catch (DatabaseTestException e) {
					
				}
			}
			
			if(numExceptions >= 3) {
				System.out.println(numExceptions  + " exceptions occured, aborting query tests");
				queryTestSuccess = false;
				break;
			}
			
			try {
				dbTestModule.clean();
				dbTestModule.add(simpleCars);
				
				time1 = System.nanoTime();
				dbTestModule.query(simpleCars);
				time2 = System.nanoTime();
				
				queryTimes[i] = (((double) time2) - ((double) time1)) * 1e-6;
				System.out.println("update test " + i + ": " + queryTimes[i]);
			} catch (DatabaseTestException e) {
				System.out.println("Exception during " + dbTestModule.databaseName() + " query testing");
				e.printStackTrace();
				numExceptions++;
				i--;
			}
		}
		
		System.out.println("Running remove tests for " + dbTestModule.databaseName());
		numExceptions = 0;
		for(int i = 0; i < numTests; i++) {
			for(int j = 0; j < prestart; j++) {
				try {
					dbTestModule.clean();
					dbTestModule.add(simpleCars);
					dbTestModule.remove(simpleCars);
				} catch (DatabaseTestException e) {
					
				}
			}
			
			if(numExceptions >= 3) {
				System.out.println(numExceptions  + " exceptions occured, aborting remove tests");
				removeTestSuccess = false;
				break;
			}
			
			try {
				dbTestModule.clean();
				dbTestModule.add(simpleCars);
				
				time1 = System.nanoTime();
				dbTestModule.remove(simpleCars);
				time2 = System.nanoTime();
				
				removeTimes[i] = (((double) time2) - ((double) time1)) * 1e-6;
				System.out.println("remove test " + i + ": " + removeTimes[i]);
			} catch (DatabaseTestException e) {
				System.out.println("Exception during " + dbTestModule.databaseName() + " remove testing");
				e.printStackTrace();
				numExceptions++;
				i--;
			}
		}
		
		
		try {
			dbTestModule.teardown();
		} catch (DatabaseTestException e) {
			System.out.println("Error tearing down database " + dbTestModule.databaseName());
			e.printStackTrace();
		}
		
		if(addTestSuccess) {
			Mean addMeanObj = new Mean();
			double addMean = addMeanObj.evaluate(addTimes);
			StandardDeviation addSDObj = new StandardDeviation();
			double addSD = addSDObj.evaluate(addTimes, addMean);
			System.out.println("add tests had a arithmetic mean of " + addMean + 
					" and a standard deviation of " + addSD);
		}
		
		if(updateTestSuccess) {
			Mean updateMeanObj = new Mean();
			double updateMean = updateMeanObj.evaluate(updateTimes);
			StandardDeviation updateSDObj = new StandardDeviation();
			double updateSD = updateSDObj.evaluate(updateTimes, updateMean);
			System.out.println("update tests had a arithmetic mean of " + updateMean + 
					" and a standard deviation of " + updateSD);
		}
		
		if(queryTestSuccess) {
			Mean queryMeanObj = new Mean();
			double queryMean = queryMeanObj.evaluate(queryTimes);
			StandardDeviation querySDObj = new StandardDeviation();
			double querySD = querySDObj.evaluate(queryTimes, queryMean);
			System.out.println("query tests had a arithmetic mean of " + queryMean + 
					" and a standard deviation of " + querySD);
		}
		
		if(removeTestSuccess) {
			Mean removeMeanObj = new Mean();
			double removeMean = removeMeanObj.evaluate(removeTimes);
			StandardDeviation removeSDObj = new StandardDeviation();
			double removeSD = removeSDObj.evaluate(removeTimes, removeMean);
			System.out.println("remove tests had a arithmetic mean of " + removeMean + 
					" and a standard deviation of " + removeSD);
		}
		
		
	}
	
	

}
