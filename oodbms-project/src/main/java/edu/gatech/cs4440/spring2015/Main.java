package edu.gatech.cs4440.spring2015;

import java.io.IOException;

import edu.gatech.cs4440.spring2015.generator.ComplexCarGenerator;
import edu.gatech.cs4440.spring2015.generator.SimpleCarGenerator;
import edu.gatech.cs4440.spring2015.model.ComplexCar;
import edu.gatech.cs4440.spring2015.model.SimpleCar;
import edu.gatech.cs4440.spring2015.testing.ComplexDatabaseTestModule;
import edu.gatech.cs4440.spring2015.testing.DatabaseTestModule;
import edu.gatech.cs4440.spring2015.testing.DummyComplexTestModule;
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
		
		ComplexDatabaseTestModule[] complexDbTestModules = {
				new DummyComplexTestModule()
		};

		String[] dbNames = new String[dbTestModules.length];
		for(int i = 0; i < dbTestModules.length; i++) {
			dbNames[i] = dbTestModules[i].databaseName();
		}
		
		SimpleCarGenerator simpleCarGenerator = new SimpleCarGenerator();
		ComplexCarGenerator complexCarGenerator = new ComplexCarGenerator();
		
		StatisticsGrapher simpleGrapher = new StatisticsGrapher("SimpleCar", dbNames);
		StatisticsGrapher complexGrapher = new StatisticsGrapher("ComplexCar", dbNames);
		
//		for(int objNum : objNums) {
//			SimpleCar[] simpleCars = simpleCarGenerator.generate(objNum);
//			
//			for(DatabaseTestModule dbTestModule : dbTestModules) {
//				DatabaseTest dbTest = new DatabaseTest(prestart, numTests, objNum, dbTestModule);
//				DatabaseTest.TestResult result = dbTest.test(simpleCars);
//				simpleGrapher.addTestResults(dbTestModule.databaseName(), objNum, result);
//			}
//			
//			try {
//				simpleGrapher.graph();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
		
		for(int objNum : objNums) { 
			ComplexCar[] complexCars = complexCarGenerator.generate(objNum);
			
			for(ComplexDatabaseTestModule complexDbTestModule : complexDbTestModules) {
				ComplexDatabaseTest complexDbTest = new ComplexDatabaseTest(prestart, numTests, objNum, complexDbTestModule);
				ComplexDatabaseTest.TestResult result = complexDbTest.test(complexCars);
				// Add results here
				if(result == null) {
					throw new RuntimeException();
				}
				
				// graph here
			}
		}


	}

}
