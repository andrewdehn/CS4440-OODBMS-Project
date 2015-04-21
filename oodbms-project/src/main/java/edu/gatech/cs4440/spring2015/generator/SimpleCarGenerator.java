package edu.gatech.cs4440.spring2015.generator;

import java.util.Random;

import edu.gatech.cs4440.spring2015.model.CarColor;
import edu.gatech.cs4440.spring2015.model.SimpleCar;

public class SimpleCarGenerator {
	
	String[] makes = {"Aston Martin",
					  "Audi",
					  "Bentley",
					  "BMW",
					  "Chevrolet", 
					  "Chrysler",
					  "Dodge",
					  "Ferrari",
					  "Fiat",
					  "Ford", 
					  "Honda", 
					  "Hyundai", 
					  "Infinity",
					  "Jaguar",
					  "Jeep",
					  "Kia",
					  "Lexus",
					  "Mercedes",
					  "Nissan",
					  "Porsche",
					  "Subaru",
					  "Tesla",
					  "Toyota",
					  "Volkswagan",
					  "Volvo"
					  };
	
	char[] alpha = {'a','b','c','d',
					'e','f','g','h',
					'i','j','k','l',
					'm','n','o','p',
					'q','r','s','t',
					'u','v','w','x',
					'y','z'};
	
	char[] vin_alphanum = {'0','1','2','3',
					   	   '4','5','6','7',
					   	   '8','9','A','B',
					   	   'C','D','E','F',
					   	   'G','H','J','K',
					   	   'L','M','N','P',
					   	   'R','S','T','U',
					   	   'V','W','X','Y',
					   	   'Z'};
	
	public SimpleCar[] generate(int num) {
		CarColor[] colors = CarColor.values();
		Random rand = new Random();
		SimpleCar[] array = new SimpleCar[num];
		for(int i = 0; i < num; i++) {
			array[i] = new SimpleCar(makes[rand.nextInt(makes.length)], 
									 randomAlphaString(rand, 8),
									 1980 + rand.nextInt(35),
									 randomVin(rand),
									 colors[rand.nextInt(colors.length)]);
		} 
		return array;
	}
	
	String randomAlphaString(Random rand, int size) {
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < size; i++) {
			builder.append(alpha[rand.nextInt(alpha.length)]);
		}
		return builder.toString();
	}
	
	String randomVin(Random rand) {
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < 17; i++) {
			builder.append(vin_alphanum[rand.nextInt(vin_alphanum.length)]);
		}
		return builder.toString();
	}

}
