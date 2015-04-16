package edu.gatech.cs4440.spring2015;

import java.io.IOException;
import java.util.ArrayList;

import edu.gatech.cs4440.spring2015.testing.DatabaseTestModule;
import edu.gatech.cs4440.spring2015.testing.DummyTestModule;
import edu.gatech.cs4440.spring2015.testing.ObjectDBTestModule;


public class Main {

	public static void main(String[] args) {

		int prestart = 0;
		int numTests = 10;
		int[] objNums = {10, 100};

		DatabaseTestModule[] dbTestModules = {
				new DummyTestModule(), 
				new ObjectDBTestModule()
		};

		for(DatabaseTestModule dbTestModule : dbTestModules) {
			StatisticsGrapher grapher = new StatisticsGrapher(dbTestModule.databaseName(), numTests);
			
			for(int objNum : objNums) {
				DatabaseTest dbTest = new DatabaseTest(prestart, numTests, objNum, dbTestModule);
				DatabaseTest.TestResult result = dbTest.test();
				grapher.addTestResults(objNum, result);
			}
			
			try {
				grapher.graph();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}


	}

}
