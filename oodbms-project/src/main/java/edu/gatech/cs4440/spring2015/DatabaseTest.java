package edu.gatech.cs4440.spring2015;

import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import edu.gatech.cs4440.spring2015.generator.SimpleCarGenerator;
import edu.gatech.cs4440.spring2015.model.SimpleCar;
import edu.gatech.cs4440.spring2015.testing.DatabaseTestException;
import edu.gatech.cs4440.spring2015.testing.DatabaseTestModule;

/**
 * Performs a series of tests against a database supported by a DatabaseTestModule.
 */
public class DatabaseTest {

	int prestart;
	int numTests;
	int numObjects;
	DatabaseTestModule dbTestModule;
	
	/**
	 * Creates a {@link DatabaseTest} object.
	 * 
	 * @param prestart 
	 * 		The number of operations to run to "warm up" the JVM.
	 * 
	 * @param numTests
	 * 		The number of tests to run per operation.
	 * 
	 * @param numObjects
	 * 		The number of objects to use.
	 * 
	 * @param dbTestModule
	 * 		The module that defines which database to test against.
	 */
	public DatabaseTest(int prestart, int numTests, int numObjects, DatabaseTestModule dbTestModule) {
		this.prestart = prestart;
		this.numTests = numTests;
		this.numObjects = numObjects;
		this.dbTestModule = dbTestModule;
	}
	
	/** 
	 * Performs the tests.
	 */
	public TestResult test(SimpleCar[] simpleCars) {
		
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
		
		System.out.println("Running add tests for " + dbTestModule.databaseName() + " with " + simpleCars.length + " objects");
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
//				System.out.println("add test " + i + ": " + addTimes[i]);
			} catch (DatabaseTestException e) {
				System.out.println("Exception during " + dbTestModule.databaseName() + " add testing");
				e.printStackTrace();
				numExceptions++;
				i--;
			}
		}
		
		
		System.out.println("Running update tests for " + dbTestModule.databaseName() + " with " + simpleCars.length + " objects");
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
//				System.out.println("update test " + i + ": " + updateTimes[i]);
			} catch (DatabaseTestException e) {
				System.out.println("Exception during " + dbTestModule.databaseName() + " update testing");
				e.printStackTrace();
				numExceptions++;
				i--;
			}
		}
		
		System.out.println("Running query tests for " + dbTestModule.databaseName() + " with " + simpleCars.length + " objects");
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
//				System.out.println("update test " + i + ": " + queryTimes[i]);
			} catch (DatabaseTestException e) {
				System.out.println("Exception during " + dbTestModule.databaseName() + " query testing");
				e.printStackTrace();
				numExceptions++;
				i--;
			}
		}
		
		System.out.println("Running remove tests for " + dbTestModule.databaseName() + " with " + simpleCars.length + " objects");
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
//				System.out.println("remove test " + i + ": " + removeTimes[i]);
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
		
		double addMean = 0, addSD = 0;
		if(addTestSuccess) {
			Mean addMeanObj = new Mean();
			addMean = addMeanObj.evaluate(addTimes);
			StandardDeviation addSDObj = new StandardDeviation();
			addSD = addSDObj.evaluate(addTimes, addMean);
			System.out.println("add tests had a arithmetic mean of " + addMean + 
					"ms and a standard deviation of " + addSD + "ms");
		}
		
		double updateMean = 0, updateSD = 0;
		if(updateTestSuccess) {
			Mean updateMeanObj = new Mean();
			updateMean = updateMeanObj.evaluate(updateTimes);
			StandardDeviation updateSDObj = new StandardDeviation();
			updateSD = updateSDObj.evaluate(updateTimes, updateMean);
			System.out.println("update tests had a arithmetic mean of " + updateMean + 
					"ms and a standard deviation of " + updateSD + "ms");
		}
		
		double queryMean = 0, querySD = 0;
		if(queryTestSuccess) {
			Mean queryMeanObj = new Mean();
			queryMean = queryMeanObj.evaluate(queryTimes);
			StandardDeviation querySDObj = new StandardDeviation();
			querySD = querySDObj.evaluate(queryTimes, queryMean);
			System.out.println("query tests had a arithmetic mean of " + queryMean + 
					"ms and a standard deviation of " + querySD + "ms");
		}
		
		double removeMean = 0, removeSD = 0;
		if(removeTestSuccess) {
			Mean removeMeanObj = new Mean();
			removeMean = removeMeanObj.evaluate(removeTimes);
			StandardDeviation removeSDObj = new StandardDeviation();
			removeSD = removeSDObj.evaluate(removeTimes, removeMean);
			System.out.println("remove tests had a arithmetic mean of " + removeMean + 
					"ms and a standard deviation of " + removeSD + "ms");
		}
		
		TestResult result = new TestResult();
		result.setAdd(addMean, addSD);
		result.setUpdate(updateMean, updateSD);
		result.setQuery(queryMean, querySD);
		result.setRemove(removeMean, removeSD);
		return result;
	}
	
	public class TestResult {
		
		double addMean;
		double addSD;
		
		double updateMean;
		double updateSD;
		
		double queryMean;
		double querySD;
		
		double removeMean;
		double removeSD;
		
		private void setAdd(double mean, double sd) {
			addMean = mean;
			addSD = sd;
		}
		
		private void setUpdate(double mean, double sd) {
			updateMean = mean;
			updateSD = sd;
		}
		
		private void setQuery(double mean, double sd) {
			queryMean = mean;
			querySD = sd;
		}
		
		private void setRemove(double mean, double sd) {
			removeMean = mean;
			removeSD = sd;
		}

		public double getAddMean() {
			return addMean;
		}

		public double getAddSD() {
			return addSD;
		}

		public double getUpdateMean() {
			return updateMean;
		}

		public double getUpdateSD() {
			return updateSD;
		}

		public double getQueryMean() {
			return queryMean;
		}

		public double getQuerySD() {
			return querySD;
		}

		public double getRemoveMean() {
			return removeMean;
		}

		public double getRemoveSD() {
			return removeSD;
		}
		
	}

}
