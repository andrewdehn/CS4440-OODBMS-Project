package edu.gatech.cs4440.spring2015;

import edu.gatech.cs4440.spring2015.generator.SimpleCarGenerator;
import edu.gatech.cs4440.spring2015.model.SimpleCar;

public class Main {
	
	public static void main(String[] args) {
		SimpleCarGenerator gen = new SimpleCarGenerator();
		SimpleCar[] cars = gen.generate(10);
		for(SimpleCar car : cars) {
			System.out.println(car);
		}
	}

}
