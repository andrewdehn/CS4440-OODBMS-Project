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
import edu.gatech.cs4440.spring2015.testing.MySQLComplexTestModule;
import edu.gatech.cs4440.spring2015.testing.MySQLTestModule;
import edu.gatech.cs4440.spring2015.testing.ObjectDBComplexTestModule;
import edu.gatech.cs4440.spring2015.testing.ObjectDBTestModule;
import edu.gatech.cs4440.spring2015.testing.PostgreSQLTestModule;


public class Main {

	public static void main(String[] args) {

		int prestart = 10;
		int numTests = 10;
		int[] objNums = {10, 100};//, 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000};

		DatabaseTestModule[] dbTestModules = {
//				new DummyTestModule(), 
//				new ObjectDBTestModule(),
//				new MySQLTestModule()
				new PostgreSQLTestModule()
		};
		
		ComplexDatabaseTestModule[] complexDbTestModules = {
//				new DummyComplexTestModule(),
				new ObjectDBComplexTestModule(),
				new MySQLComplexTestModule()
		};

		String[] dbNames = new String[dbTestModules.length];
		for(int i = 0; i < dbTestModules.length; i++) {
			dbNames[i] = dbTestModules[i].databaseName();
		}
		
		String[] complexDbNames = new String[complexDbTestModules.length];
		for(int i = 0; i < complexDbTestModules.length; i++) {
			complexDbNames[i] = complexDbTestModules[i].databaseName();
		}
		
		SimpleCarGenerator simpleCarGenerator = new SimpleCarGenerator();
		ComplexCarGenerator complexCarGenerator = new ComplexCarGenerator();
		
		StatisticsGrapher simpleGrapher = new StatisticsGrapher("SimpleCar", dbNames);
		StatisticsGrapher complexGrapher = new StatisticsGrapher("ComplexCar", complexDbNames);
		
		for(int objNum : objNums) {
			SimpleCar[] simpleCars = simpleCarGenerator.generate(objNum);
			
			for(DatabaseTestModule dbTestModule : dbTestModules) {
				DatabaseTest dbTest = new DatabaseTest(prestart, numTests, objNum, dbTestModule);
				TestResult result = dbTest.test(simpleCars);
				simpleGrapher.addTestResults(dbTestModule.databaseName(), objNum, result);
			}
			
			try {
				simpleGrapher.graph();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		for(int objNum : objNums) { 
			ComplexCar[] complexCars = complexCarGenerator.generate(objNum);
			
			for(ComplexDatabaseTestModule complexDbTestModule : complexDbTestModules) {
				ComplexDatabaseTest complexDbTest = new ComplexDatabaseTest(prestart, numTests, objNum, complexDbTestModule);
				TestResult result = complexDbTest.test(complexCars);
				if(result == null) {
					throw new RuntimeException();
				}
				complexGrapher.addTestResults(complexDbTestModule.databaseName(), objNum, result);
			}
			
			try {
				complexGrapher.graph();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println("Testing complete!");

	}

}
