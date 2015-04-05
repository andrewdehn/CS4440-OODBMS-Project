package edu.gatech.cs4440.spring2015.model;

public class SimpleCar {
	
	private String make;
	private String model;
	private int year;
	private String vin;		// This should probably be the primary key
	private CarColor color;
	
	public SimpleCar(String make, String model, int year, String vin, CarColor color) {
		this.make = make;
		this.model = model;
		this.year = year;
		this.vin = vin;
		this.color = color;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public CarColor getColor() {
		return color;
	}

	public void setColor(CarColor color) {
		this.color = color;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(color.toString()).append(" ");
		builder.append(make).append(" ");
		builder.append(model).append(" ");
		builder.append(year).append(": ");
		builder.append(vin);
		return builder.toString();
	}
}
