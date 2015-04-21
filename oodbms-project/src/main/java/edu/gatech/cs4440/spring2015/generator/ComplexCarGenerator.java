package edu.gatech.cs4440.spring2015.generator;

import java.util.List;
import java.util.Random;

import edu.gatech.cs4440.spring2015.model.Accident;
import edu.gatech.cs4440.spring2015.model.ComplexCar;
import edu.gatech.cs4440.spring2015.model.Maintenance;
import edu.gatech.cs4440.spring2015.model.SimpleCar;

public class ComplexCarGenerator {

	Random rand = new Random();
	SimpleCarGenerator simpleGenerator;
	
	public ComplexCarGenerator() {
		simpleGenerator = new SimpleCarGenerator();
	}
	
	public ComplexCar[] generate(int num) {
		ComplexCar[] complexCars = new ComplexCar[num];
		SimpleCar[] simpleCars = simpleGenerator.generate(num);
		for(int i = 0; i < num; i++) {
			SimpleCar simpleCar = simpleCars[i];
			complexCars[i] = new ComplexCar(simpleCar.getMake(), simpleCar.getModel(), simpleCar.getYear(), simpleCar.getVin(), simpleCar.getColor());
			generateAccidents(complexCars[i]);
			generateMaintenance(complexCars[i]);
		}
		return complexCars;
	}
	
	void generateAccidents(ComplexCar car) {
		for(int i = 0; i < rand.nextInt(10); i++) {
			car.getAccidents().add(new Accident(simpleGenerator.randomAlphaString(rand, 10), simpleGenerator.randomAlphaString(rand, 20), (double) Math.round(rand.nextDouble() * 100) / 100));
		}
	}
	
	void generateMaintenance(ComplexCar car) {
		for(int i = 0; i < rand.nextInt(10); i++) {
			car.getMaintenance().add(new Maintenance(simpleGenerator.randomAlphaString(rand, 10), simpleGenerator.randomAlphaString(rand, 20), (double) Math.round(rand.nextDouble() * 100) / 100));
		}
	}

}
