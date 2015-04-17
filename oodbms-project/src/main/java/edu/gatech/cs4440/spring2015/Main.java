package edu.gatech.cs4440.spring2015;

import java.io.IOException;

import edu.gatech.cs4440.spring2015.testing.DatabaseTestModule;
import edu.gatech.cs4440.spring2015.testing.DummyTestModule;
import edu.gatech.cs4440.spring2015.testing.ObjectDBTestModule;


public class Main {

	public static void main(String[] args) {

		int prestart = 10;
		int numTests = 10;
		int[] objNums = {10, 100, 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000};

		DatabaseTestModule[] dbTestModules = {
				new DummyTestModule(), 
				new ObjectDBTestModule()
		};

		String[] dbNames = new String[dbTestModules.length];
		for(int i = 0; i < dbTestModules.length; i++) {
			dbNames[i] = dbTestModules[i].databaseName();
		}
		StatisticsGrapher grapher = new StatisticsGrapher(dbNames);
		
		for(DatabaseTestModule dbTestModule : dbTestModules) {
			for(int objNum : objNums) {
				DatabaseTest dbTest = new DatabaseTest(prestart, numTests, objNum, dbTestModule);
				DatabaseTest.TestResult result = dbTest.test();
				grapher.addTestResults(dbTestModule.databaseName(), objNum, result);
			}
			
			try {
				grapher.graph();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}


	}

}
